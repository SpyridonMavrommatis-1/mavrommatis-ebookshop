package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Author} entities.
 * Defines the contract for operations related to authors.
 */
public interface AuthorService {

    /**
     * Retrieve all authors from the database.
     *
     * @return a list of all authors.
     */
    List<Author> findAll();

    /**
     * Retrieve a specific author by ID.
     *
     * @param id the ID of the author to find.
     * @return an Optional containing the author if found, or empty otherwise.
     */
    Optional<Author> findById(Integer id);

    /**
     * Save a new author to the database.
     *
     * @param author the author to be saved.
     * @return the saved author entity.
     */
    Author save(Author author);

    /**
     * Save multiple authors to the database.
     *
     * @param authors list of authors to be saved.
     * @return list of saved author entities.
     */
    List<Author> saveAll(List<Author> authors);

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
