package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link AuthorEntity} entities.
 * Defines the contract for operations related to authors.
 */
public interface AuthorService {

    /**
     * Retrieve all authors from the database.
     *
     * @return a list of all authors.
     */
    List<AuthorEntity> findAll();

    /**
     * Retrieve a specific author by ID.
     *
     * @param id the ID of the author to find.
     * @return an Optional containing the author if found, or empty otherwise.
     */
    Optional<AuthorEntity> findById(Integer id);

    /**
     * Save a new author to the database.
     *
     * @param author the author to be saved.
     * @return the saved author entity.
     */
    AuthorEntity save(AuthorEntity author);

    /**
     * Save multiple authors to the database.
     *
     * @param authors list of authors to be saved.
     * @return list of saved author entities.
     */
    List<AuthorEntity> saveAll(List<AuthorEntity> authors);

    /**
     * Delete an author by its ID.
     *
     * @param id the ID of the author to be deleted.
     */
    void deleteById(Integer id);

    /**
     * Delete multiple authors by their IDs.
     *
     * @param ids list of IDs of authors to be deleted.
     */
    void deleteAllById(List<Integer> ids);
}
