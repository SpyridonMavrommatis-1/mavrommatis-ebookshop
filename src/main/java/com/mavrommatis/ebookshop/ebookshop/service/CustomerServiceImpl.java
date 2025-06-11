package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link CustomerService}, handling business logic
 * and mapping between DTOs and entities via {@link CustomerMapper}.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    /**
     * Constructs a new CustomerServiceImpl with required dependencies.
     *
     * @param repository the repository for Customer entities
     * @param mapper     the mapper for converting between DTOs and entities
     */
    @Autowired
    public CustomerServiceImpl(CustomerRepository repository,
                               CustomerMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CustomerResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerResponseDTO findById(Integer id) {
        CustomerEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CustomerResponseDTO update(Integer id, CustomerRequestDTO dto) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cannot update. Customer not found: " + id);
        }
        CustomerEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        // Update basic fields
        existing.setUsername(dto.getUsername());
        existing.setPassword(dto.getPassword());
        existing.setEmail(dto.getEmail());

        // Handle nested details
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Customer not found: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
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
}
