package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link AuthorDetailsEntity} entities.
 * Defines the contract for business logic related to author personal information.
 */
public interface AuthorDetailsService {

    /**
     * Retrieve all {@link AuthorDetailsEntity} records from the database.
     *
     * @return a list of all AuthorDetails.
     */
    List<AuthorDetailsEntity> findAll();

    /**
     * Retrieve a specific {@link AuthorDetailsEntity} record by its ID.
     *
     * @param id the unique identifier of the author details.
     * @return an {@link Optional} containing the AuthorDetails if found, or empty if not.
     */
    Optional<AuthorDetailsEntity> findById(Integer id);

    /**
     * Save a new or updated {@link AuthorDetailsEntity} record.
     *
     * @param authorDetails the AuthorDetails entity to be saved.
     * @return the persisted AuthorDetails instance.
     */
    AuthorDetailsEntity save(AuthorDetailsEntity authorDetails);

    /**
     * Save multiple {@link AuthorDetailsEntity} records in a single batch operation.
     *
     * @param authorDetails list of AuthorDetails entities to save.
     * @return list of saved AuthorDetails instances.
     */
    List<AuthorDetailsEntity> saveAll(List<AuthorDetailsEntity> authorDetails);

    /**
     * Delete an {@link AuthorDetailsEntity} record by its ID.
     * If the ID doesn't exist, no action is performed.
     *
     * @param id the ID of the record to delete.
     */
    void deleteById(Integer id);

    /**
     * Delete multiple {@link AuthorDetailsEntity} records by their IDs.
     * Non-existing IDs are ignored.
     *
     * @param ids list of IDs to be deleted.
     */
    void deleteAllById(List<Integer> ids);
}
