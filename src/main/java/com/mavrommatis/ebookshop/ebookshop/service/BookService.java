package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link BookEntity} entities.
 * Defines methods for CRUD operations related to books.
 */
public interface BookService {

    /**
     * Retrieve all books from the database.
     *
     * @return a list of all {@link BookEntity} entities
     */
    List<BookEntity> findAll();

    /**
     * Retrieve a book by its unique ID.
     *
     * @param id the ID of the book
     * @return an {@link Optional} containing the {@link BookEntity}, if found
     */
    Optional<BookEntity> findById(Integer id);

    /**
     * Save a single book to the database.
     *
     * @param book the book to be saved
     * @return the saved {@link BookEntity} entity
     */
    BookEntity save(BookEntity book);

    /**
     * Save multiple books to the database at once.
     *
     * @param books a list of books to be saved
     * @return the list of saved {@link BookEntity} entities
     */
    List<BookEntity> saveAll(List<BookEntity> books);

    /**
     * Update an existing book in the database.
     * The book must have a valid ID and already exist in the database.
     *
     * @param book the {@link BookEntity} entity containing updated fields
     * @return the updated {@link BookEntity}
     * @throws RuntimeException if the book does not exist
     */
    BookEntity update(BookEntity book);


    /**
     * Delete a book by its ID, if it exists.
     *
     * @param id the ID of the book to be deleted
     */
    void deleteById(Integer id);

    /**
     * Delete multiple books by their IDs, if they exist.
     *
     * @param ids a list of IDs for the books to be deleted
     */
    void deleteAllById(List<Integer> ids);
}

