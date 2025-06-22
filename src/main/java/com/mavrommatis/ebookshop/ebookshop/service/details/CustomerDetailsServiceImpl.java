package com.mavrommatis.ebookshop.ebookshop.service.details;
import com.mavrommatis.ebookshop.ebookshop.dao.CustomerDetailsRepository;
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

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final CustomerDetailsRepository repository;
    private final CustomerMapper mapper;

    public CustomerDetailsServiceImpl(CustomerDetailsRepository repository,
                                      CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CustomerDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDetailsDTO findById(Integer customerId) {
        if (!isAdmin() && !getCurrentUsername().equals(getUsernameByCustomerId(customerId))) {
            throw new CustomerAccessDeniedException("You can only view your own details.");
        }

        CustomerDetailsEntity entity = repository.findById(customerId)
                .orElseThrow(() -> new CustomerDetailsNotFoundException(customerId));

        return mapper.toDto(entity);
    }

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

    @Override
    @Transactional
    public List<CustomerDetailsDTO> saveAll(List<CustomerDetailsDTO> dtos) {
        List<CustomerDetailsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        List<CustomerDetailsEntity> saved = repository.saveAll(entities);
        return saved.stream().map(mapper::toDto).collect(Collectors.toList());
    }

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

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }
        return principal.toString();
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Dummy method: Replace this logic with actual username lookup via repository if needed.
     */
    private String getUsernameByCustomerId(Integer customerId) {
        throw new UnsupportedOperationException("Username lookup for customerId not implemented");
    }
}