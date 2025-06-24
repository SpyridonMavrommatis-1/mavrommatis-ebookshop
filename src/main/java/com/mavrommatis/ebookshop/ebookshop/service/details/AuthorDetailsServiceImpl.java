package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.details.AuthorDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.author.AuthorDetailsNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing AuthorDetails entities.
 * <p>
 * Provides CRUD operations for author details and maps between DTOs and entities
 * using the {@link AuthorMapper}. Ensures data integrity by checking existence
 * before delete operations.
 * </p>
 */
@Service
public class AuthorDetailsServiceImpl implements AuthorDetailsService {

    /**
     * Repository for accessing author details data.
     */
    private final AuthorDetailsRepository repository;

    /**
     * Mapper for converting between {@link AuthorDetailsEntity} and {@link AuthorDetailsDTO}.
     */
    private final AuthorMapper mapper;

    /**
     * Constructs the AuthorDetails service with the required repository and mapper.
     *
     * @param repository the repository to interact with persistence layer
     * @param mapper     the mapper for transforming data objects
     */
    public AuthorDetailsServiceImpl(AuthorDetailsRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves all author details.
     *
     * @return a list of all author details as DTOs
     */
    @Override
    public List<AuthorDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific author's details by their ID.
     *
     * @param authorId the ID of the author
     * @return the author details as a DTO
     * @throws AuthorDetailsNotFoundException if no author details are found for the given ID
     */
    @Override
    public AuthorDetailsDTO findById(Integer authorId) {
        AuthorDetailsEntity entity = repository.findById(authorId)
                .orElseThrow(() -> new AuthorDetailsNotFoundException(authorId));
        return mapper.toDto(entity);
    }

    /**
     * Saves new author details.
     *
     * @param dto the author details DTO to save
     * @return the saved author details as a DTO
     */
    @Override
    @Transactional
    public AuthorDetailsDTO save(AuthorDetailsDTO dto) {
        AuthorDetailsEntity entity = mapper.toEntity(dto);
        AuthorDetailsEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /**
     * Saves multiple author details in batch.
     *
     * @param dtos a list of author detail DTOs
     * @return the list of saved author details as DTOs
     */
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

    /**
     * Deletes a specific author's details by ID.
     *
     * @param authorId the ID of the author
     * @throws AuthorDetailsNotFoundException if no matching entity is found
     */
    @Override
    public void deleteById(Integer authorId) {
        if (!repository.existsById(authorId)) {
            throw new AuthorDetailsNotFoundException(authorId);
        }
        repository.deleteById(authorId);
    }

    /**
     * Deletes multiple author details by their IDs.
     *
     * @param authorIds the list of author IDs
     * @throws AuthorDetailsNotFoundException if any ID does not correspond to an existing entity
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> authorIds) {
        for (Integer id : authorIds) {
            if (!repository.existsById(id)) {
                throw new AuthorDetailsNotFoundException(id);
            }
        }
        repository.deleteAllById(authorIds);
    }
}
