package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link CustomerDetailsService}, handling CRUD operations
 * and mapping between DTOs and entities via {@link CustomerMapper}.
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
     * {@inheritDoc}
     */
    @Override
    public List<CustomerDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CustomerDetailsDTO findById(Integer customerId) {
        CustomerDetailsEntity entity = repository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found: " + customerId));
        return mapper.toDto(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CustomerDetailsDTO save(CustomerDetailsDTO dto) {
        CustomerDetailsEntity entity = mapper.toEntity(dto);
        CustomerDetailsEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * {@inheritDoc}
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
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer customerId) {
        if (!repository.existsById(customerId)) {
            throw new RuntimeException("CustomerDetails not found: " + customerId);
        }
        repository.deleteById(customerId);
    }

    /**
     * {@inheritDoc}
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
}
