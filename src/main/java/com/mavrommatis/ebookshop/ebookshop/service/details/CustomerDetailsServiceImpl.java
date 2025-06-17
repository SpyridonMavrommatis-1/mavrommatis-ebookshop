package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link CustomerDetailsService}, handling CRUD operations,
 * identity-based authorization, and mapping between DTOs and entities via {@link CustomerMapper}.
 *
 * <p>
 * Non-admin users are only allowed to view or modify their own details,
 * while admin users have unrestricted access.
 * </p>
 *
 * @see CustomerDetailsRepository
 * @see CustomerMapper
 */
@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final CustomerDetailsRepository repository;
    private final CustomerMapper mapper;

    /**
     * Constructs a new CustomerDetailsServiceImpl with required dependencies.
     *
     * @param repository the repository for CustomerDetailsEntity persistence
     * @param mapper     the mapper for converting between DTOs and entities
     */
    public CustomerDetailsServiceImpl(CustomerDetailsRepository repository,
                                      CustomerMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    /**
     * Retrieve all customer detail records.
     * Restricted via controller-level to EMPLOYEE and ADMIN.
     *
     * @return list of all customer details
     */
    @Override
    public List<CustomerDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific customer's details.
     * Throws {@link AccessDeniedException} if caller is not admin and requests other user's details.
     *
     * @param customerId the customer ID
     * @return the corresponding details DTO
     */
    @Override
    public CustomerDetailsDTO findById(Integer customerId) {
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(customerId))) {
            throw new AccessDeniedException("You can only view your own details.");
        }
        CustomerDetailsEntity entity = repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found: " + customerId));
        return mapper.toDto(entity);
    }

    /**
     * Save or update a customer's details.
     * Only the owner or an admin is allowed to update.
     *
     * @param dto the details to save
     * @return the saved DTO
     */
    @Override
    @Transactional
    public CustomerDetailsDTO save(CustomerDetailsDTO dto) {
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(dto.getCustomerId()))) {
            throw new AccessDeniedException("You can only update your own details.");
        }
        CustomerDetailsEntity entity = mapper.toEntity(dto);
        CustomerDetailsEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Save a list of customer details.
     * Only accessible by ADMIN.
     *
     * @param dtos list of detail DTOs
     * @return list of saved DTOs
     */
    @Override
    @Transactional
    public List<CustomerDetailsDTO> saveAll(List<CustomerDetailsDTO> dtos) {
        List<CustomerDetailsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        List<CustomerDetailsEntity> saved = repository.saveAll(entities);
        return saved.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Delete customer details by ID.
     * Only the owner or admin can delete.
     *
     * @param customerId the ID of the customer to delete
     */
    @Override
    public void deleteById(Integer customerId) {
        if (!repository.existsById(customerId)) {
            throw new RuntimeException("CustomerDetails not found: " + customerId);
        }
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(customerId))) {
            throw new AccessDeniedException("You can only delete your own details.");
        }
        repository.deleteById(customerId);
    }

    /**
     * Delete multiple customer details by their IDs.
     * Only accessible by ADMIN.
     *
     * @param customerIds list of IDs to delete
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> customerIds) {
        for (Integer id : customerIds) {
            if (!repository.existsById(id)) {
                throw new RuntimeException("CustomerDetails not found: " + id);
            }
        }
        repository.deleteAllById(customerIds);
    }

    /**
     * Utility: Gets username of the current authenticated user.
     *
     * @return current username
     */
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }

    /**
     * Utility: Checks if the current user has the ADMIN role.
     *
     * @return true if ADMIN, false otherwise
     */
    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Dummy method: Replace with actual DB/service logic to resolve username from customer ID.
     *
     * @param customerId the customer ID
     * @return username string
     */
    private String getUsernameByCustomerId(Integer customerId) {
        // TODO: Inject CustomerRepository if needed to resolve username
        throw new UnsupportedOperationException("Username resolution not implemented");
    }
}