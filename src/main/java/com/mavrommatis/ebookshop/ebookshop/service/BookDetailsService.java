package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link BookDetailsEntity} entities.
 * Defines CRUD operations for working with detailed book information.
 */
public interface BookDetailsService {

    /**
     * Retrieve all book details records from the database.
     *
     * @return a list of all {@link BookDetailsEntity}
     */
    List<BookDetailsEntity> findAll();

    /**
     * Retrieve a specific {@link BookDetailsEntity} record by its ID.
     *
     * @param id the ID of the book details
     * @return an {@link Optional} containing the found entity or empty if not found
     */
    Optional<BookDetailsEntity> findById(Integer id);

    /**
     * Save a new {@link BookDetailsEntity} record to the database.
     * If the record already exists, it will be updated.
     *
     * @param bookDetails the book details to save
     * @return the saved {@link BookDetailsEntity} entity
     */
    BookDetailsEntity save(BookDetailsEntity bookDetails);

    /**
     * Save multiple {@link BookDetailsEntity} records at once.
     *
     * @param bookDetails a list of book details to save
     * @return a list of saved {@link BookDetailsEntity} entities
     */
    List<BookDetailsEntity> saveAll(List<BookDetailsEntity> bookDetails);

    /**
     * Delete a specific {@link BookDetailsEntity} record by its ID if it exists.
     *
     * @param id the ID of the book details to delete
     */
    void deleteById(Integer id);

    /**
     * Delete multiple {@link BookDetailsEntity} records by their IDs if they exist.
     *
     * @param ids a list of IDs representing book details to delete
     */
    void deleteAllById(List<Integer> ids);
}
