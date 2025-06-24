package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.basic.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerAccessDeniedException;
import com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Customer entities.
 * <p>
 * Supports full CRUD operations with access control and ownership validation.
 * Applies role-based restrictions for viewing, updating, and deleting customers.
 * </p>
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    /**
     * Repository used for persistence operations on Customer entities.
     */
    private final CustomerRepository repository;

    /**
     * Mapper used for converting between Customer entities and DTOs.
     */
    private final CustomerMapper mapper;

    /**
     * Constructs the service with required dependencies.
     *
     * @param repository the repository to access customer data
     * @param mapper     the mapper to transform between DTO and entity
     */
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves all customers. Only ADMIN and EMPLOYEE can view all.
     * Regular customers can only view their own record.
     */
    @Override
    public List<CustomerResponseDTO> findAll() {
        if (isAdmin() || isEmployee()) {
            return repository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
        }
        String currentUsername = getCurrentUsername();
        return repository.findAll().stream()
                .filter(c -> c.getUsername().equals(currentUsername))
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific customer by ID.
     * Access restricted to admins/employees or self-owner.
     *
     * @param id the ID of the customer to retrieve
     * @return the matching customer DTO
     */
    @Override
    public CustomerResponseDTO findById(Integer id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!(isAdmin() || isEmployee()) && !entity.getUsername().equals(getCurrentUsername())) {
            throw new CustomerAccessDeniedException("You can only view your own profile.");
        }

        return mapper.toResponse(entity);
    }

    /**
     * Saves a new customer with optional details.
     * Associates the record with the currently authenticated user.
     *
     * @param dto the customer request DTO
     * @return the saved customer DTO
     */
    @Override
    @Transactional
    public CustomerResponseDTO save(CustomerRequestDTO dto) {
        CustomerEntity customer = mapper.toEntity(dto);
        customer.setUsername(getCurrentUsername());

        if (dto.getCustomerDetails() != null) {
            CustomerDetailsEntity details = mapper.toEntity(dto.getCustomerDetails());
            details.setCustomer(customer);
            customer.setCustomerDetails(details);
        }

        return mapper.toResponse(repository.save(customer));
    }

    /**
     * Updates an existing customer by ID. Ensures only admins or self-owner can update.
     * Admins can update the username, others cannot.
     *
     * @param id  the ID of the customer to update
     * @param dto the updated customer data
     * @return the updated customer DTO
     */
    @Override
    @Transactional
    public CustomerResponseDTO update(Integer id, CustomerRequestDTO dto) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!isAdmin() && !existing.getUsername().equals(getCurrentUsername())) {
            throw new CustomerAccessDeniedException("You can only update your own profile.");
        }

        if (isAdmin()) {
            existing.setUsername(dto.getUsername());
        }

        existing.setPassword(dto.getPassword());
        existing.setEmail(dto.getEmail());

        if (dto.getCustomerDetails() != null) {
            CustomerDetailsEntity newDetails = mapper.toEntity(dto.getCustomerDetails());
            if (existing.getCustomerDetails() == null) {
                newDetails.setCustomer(existing);
                existing.setCustomerDetails(newDetails);
            } else {
                CustomerDetailsEntity managed = existing.getCustomerDetails();
                managed.setFirstName(newDetails.getFirstName());
                managed.setLastName(newDetails.getLastName());
                managed.setAddress(newDetails.getAddress());
                managed.setPhone(newDetails.getPhone());
            }
        }

        return mapper.toResponse(repository.save(existing));
    }

    /**
     * Deletes a customer by ID, ensuring access restrictions.
     *
     * @param id the ID of the customer to delete
     */
    @Override
    public void deleteById(Integer id) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!isAdmin() && !existing.getUsername().equals(getCurrentUsername())) {
            throw new CustomerAccessDeniedException("You can only delete your own profile.");
        }

        repository.deleteById(id);
    }

    /**
     * Deletes multiple customers by their IDs. Throws if any do not exist.
     *
     * @param ids the list of customer IDs to delete
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repository.existsById(id)) {
                throw new CustomerNotFoundException(id);
            }
        }
        repository.deleteAllById(ids);
    }

    /**
     * Extracts the username from the current security context.
     *
     * @return the username of the authenticated user
     */
    private String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) return userDetails.getUsername();
        if (principal instanceof Jwt jwt) return jwt.getClaimAsString("preferred_username");

        return authentication.getName();
    }

    /**
     * Checks whether the current user has the ADMIN role.
     *
     * @return true if the user is an admin; false otherwise
     */
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Checks whether the current user has the EMPLOYEE role.
     *
     * @return true if the user is an employee; false otherwise
     */
    private boolean isEmployee() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));
    }
}
