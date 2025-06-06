package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link BookDetails} entities.
 * Defines CRUD operations for working with detailed book information.
 */
public interface BookDetailsService {

    /**
     * Retrieve all book details records from the database.
     *
     * @return a list of all {@link BookDetails}
     */
    List<BookDetails> findAll();

    /**
     * Retrieve a specific {@link BookDetails} record by its ID.
     *
     * @param id the ID of the book details
     * @return an {@link Optional} containing the found entity or empty if not found
     */
    Optional<BookDetails> findById(Integer id);

    /**
     * Save a new {@link BookDetails} record to the database.
     * If the record already exists, it will be updated.
     *
     * @param bookDetails the book details to save
     * @return the saved {@link BookDetails} entity
     */
    BookDetails save(BookDetails bookDetails);

    /**
     * Save multiple {@link BookDetails} records at once.
     *
     * @param bookDetails a list of book details to save
     * @return a list of saved {@link BookDetails} entities
     */
    List<BookDetails> saveAll(List<BookDetails> bookDetails);

    /**
     * Delete a specific {@link BookDetails} record by its ID if it exists.
     *
     * @param id the ID of the book details to delete
     */
    void deleteById(Integer id);

    /**
     * Delete multiple {@link BookDetails} records by their IDs if they exist.
     *
     * @param ids a list of IDs representing book details to delete
     */
    void deleteAllById(List<Integer> ids);
}
