package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link AuthorDetailsService}, mapping via {@link AuthorMapper}.
 */
@Service
public class AuthorDetailsServiceImpl implements AuthorDetailsService {

    private final AuthorDetailsRepository repository;
    private final AuthorMapper mapper;

    /**
     * Constructor injection of repository and mapper.
     *
     * @param repository the DAO for AuthorDetailsEntity
     * @param mapper     the mapper for DTO ⇄ Entity conversions
     */
    public AuthorDetailsServiceImpl(AuthorDetailsRepository repository,
                                    AuthorMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    @Override
    public List<AuthorDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDetailsDTO findById(Integer authorId) {
        AuthorDetailsEntity entity = repository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found: " + authorId));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public AuthorDetailsDTO save(AuthorDetailsDTO dto) {
        // Map DTO → Entity
        AuthorDetailsEntity entity = mapper.toEntity(dto);
        // Persist (insert or update)
        AuthorDetailsEntity saved = repository.save(entity);
        // Map back → DTO
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<AuthorDetailsDTO> saveAll(List<AuthorDetailsDTO> dtos) {
        List<AuthorDetailsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
        List<AuthorDetailsEntity> saved = repository.saveAll(entities);
        return saved.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer authorId) {
        if (!repository.existsById(authorId)) {
            throw new RuntimeException("AuthorDetails not found: " + authorId);
        }
        repository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> authorIds) {
        for (Integer id : authorIds) {
            if (!repository.existsById(id)) {
                throw new RuntimeException("AuthorDetails not found: " + id);
            }
        }
        repository.deleteAllById(authorIds);
    }
}
