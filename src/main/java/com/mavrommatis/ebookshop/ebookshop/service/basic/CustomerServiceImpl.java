package com.mavrommatis.ebookshop.ebookshop.service.basic;
import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
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

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

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

    @Override
    public CustomerResponseDTO findById(Integer id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!(isAdmin() || isEmployee()) && !entity.getUsername().equals(getCurrentUsername())) {
            throw new CustomerAccessDeniedException("You can only view your own profile.");
        }

        return mapper.toResponse(entity);
    }

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

    @Override
    public void deleteById(Integer id) {
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (!isAdmin() && !existing.getUsername().equals(getCurrentUsername())) {
            throw new CustomerAccessDeniedException("You can only delete your own profile.");
        }

        repository.deleteById(id);
    }

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

    private String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) return userDetails.getUsername();
        if (principal instanceof Jwt jwt) return jwt.getClaimAsString("preferred_username");

        return authentication.getName();
    }

    private boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    private boolean isEmployee() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_EMPLOYEE"));
    }
}