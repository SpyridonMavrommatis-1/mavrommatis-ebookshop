package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.details.CustomerDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerAccessDeniedException;
import com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerDetailsNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing CustomerDetails entities.
 * <p>
 * Provides access control, DTO mapping, and persistence logic
 * for managing detailed customer information.
 * </p>
 */
@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    /**
     * Repository responsible for CustomerDetailsEntity persistence.
     */
    private final CustomerDetailsRepository repository;

    /**
     * Mapper for converting between CustomerDetailsDTO and CustomerDetailsEntity.
     */
    private final CustomerMapper mapper;

    /**
     * Constructs a new CustomerDetailsServiceImpl with the necessary dependencies.
     *
     * @param repository the CustomerDetails repository
     * @param mapper     the CustomerMapper for DTO conversions
     */
    public CustomerDetailsServiceImpl(CustomerDetailsRepository repository,
                                      CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves all customer details without access restrictions.
     *
     * @return list of all CustomerDetailsDTOs
     */
    @Override
    public List<CustomerDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves customer details by ID with access control.
     *
     * @param customerId the ID of the customer
     * @return the corresponding CustomerDetailsDTO
     * @throws CustomerAccessDeniedException      if access is denied
     * @throws CustomerDetailsNotFoundException   if the customer details are not found
     */
    @Override
    public CustomerDetailsDTO findById(Integer customerId) {
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(customerId))) {
            throw new CustomerAccessDeniedException("You can only view your own details.");
        }

        CustomerDetailsEntity entity = repository.findById(customerId)
                .orElseThrow(() -> new CustomerDetailsNotFoundException(customerId));

        return mapper.toDto(entity);
    }

    /**
     * Saves new customer details with access control.
     *
     * @param dto the CustomerDetailsDTO to save
     * @return the saved DTO
     * @throws CustomerAccessDeniedException if the user is not authorized
     */
    @Override
    @Transactional
    public CustomerDetailsDTO save(CustomerDetailsDTO dto) {
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(dto.getCustomerId()))) {
            throw new CustomerAccessDeniedException("You can only update your own details.");
        }

        CustomerDetailsEntity entity = mapper.toEntity(dto);
        CustomerDetailsEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Saves a list of customer details without access control.
     *
     * @param dtos the list of CustomerDetailsDTOs to save
     * @return list of saved DTOs
     */
    @Override
    @Transactional
    public List<CustomerDetailsDTO> saveAll(List<CustomerDetailsDTO> dtos) {
        List<CustomerDetailsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        List<CustomerDetailsEntity> saved = repository.saveAll(entities);
        return saved.stream().map(mapper::toDto).collect(Collectors.toList());
    }

    /**
     * Deletes customer details by ID with access control.
     *
     * @param customerId the ID to delete
     * @throws CustomerAccessDeniedException     if access is denied
     * @throws CustomerDetailsNotFoundException  if the ID is not found
     */
    @Override
    public void deleteById(Integer customerId) {
        if (!repository.existsById(customerId)) {
            throw new CustomerDetailsNotFoundException(customerId);
        }

        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(customerId))) {
            throw new CustomerAccessDeniedException("You can only delete your own details.");
        }

        repository.deleteById(customerId);
    }

    /**
     * Deletes multiple customer details entries by ID.
     *
     * @param customerIds list of IDs to delete
     * @throws CustomerDetailsNotFoundException if any ID does not exist
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> customerIds) {
        for (Integer id : customerIds) {
            if (!repository.existsById(id)) {
                throw new CustomerDetailsNotFoundException(id);
            }
        }

        repository.deleteAllById(customerIds);
    }

    /**
     * Retrieves the username of the currently authenticated user.
     *
     * @return the current username
     */
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }

    /**
     * Checks if the current user has the ADMIN role.
     *
     * @return true if the user has admin privileges
     */
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Dummy method: Replace with real username resolution logic using a repository if needed.
     *
     * @param customerId the customer ID
     * @return the username of the customer
     * @throws UnsupportedOperationException always, since this is a stub
     */
    private String getUsernameByCustomerId(Integer customerId) {
        throw new UnsupportedOperationException("Username lookup for customerId not implemented");
    }
}
