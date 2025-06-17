package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link CustomerService}, handling business logic,
 * username-based ownership enforcement, and DTO-to-entity mapping via {@link CustomerMapper}.
 *
 * <p>
 * This class implements security-sensitive logic to restrict access to resources
 * based on role and identity. Non-admin users are only allowed to operate on their
 * own customer records (matched by username).
 * </p>
 *
 * <p>
 * Admins have unrestricted access to all records.
 * </p>
 *
 * @see CustomerRepository
 * @see CustomerMapper
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDTO findById(Integer id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        if (!isAdmin() && !entity.getUsername().equals(getCurrentUsername())) {
            throw new AccessDeniedException("You can only view your own profile.");
        }

        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public CustomerResponseDTO save(CustomerRequestDTO dto) {
        CustomerEntity customer = mapper.toEntity(dto);

        if (dto.getCustomerDetails() != null) {
            CustomerDetailsEntity details = mapper.toEntity(dto.getCustomerDetails());
            details.setCustomer(customer);
            customer.setCustomerDetails(details);
        }

        CustomerEntity saved = repository.save(customer);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CustomerResponseDTO update(Integer id, CustomerRequestDTO dto) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        if (!isAdmin() && !existing.getUsername().equals(getCurrentUsername())) {
            throw new AccessDeniedException("You can only update your own profile.");
        }

        existing.setUsername(dto.getUsername());
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

        CustomerEntity updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteById(Integer id) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        if (!isAdmin() && !existing.getUsername().equals(getCurrentUsername())) {
            throw new AccessDeniedException("You can only delete your own profile.");
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Customer not found: " + id);
            }
        }
        repository.deleteAllById(ids);
    }

    /**
     * Retrieves the username of the currently authenticated user.
     */
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return principal.toString(); // fallback
    }

    /**
     * Checks if the current user has the ADMIN role.
     */
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
