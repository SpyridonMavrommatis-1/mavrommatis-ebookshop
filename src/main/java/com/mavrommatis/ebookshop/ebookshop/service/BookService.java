package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Book} entities.
 * Defines methods for CRUD operations related to books.
 */
public interface BookService {

    /**
     * Retrieve all books from the database.
     *
     * @return a list of all {@link Book} entities
     */
    List<Book> findAll();

    /**
     * Retrieve a book by its unique ID.
     *
     * @param id the ID of the book
     * @return an {@link Optional} containing the {@link Book}, if found
     */
    Optional<Book> findById(Integer id);

    /**
     * Save a single book to the database.
     *
     * @param book the book to be saved
     * @return the saved {@link Book} entity
     */
    Book save(Book book);

    /**
     * Save multiple books to the database at once.
     *
     * @param books a list of books to be saved
     * @return the list of saved {@link Book} entities
     */
    List<Book> saveAll(List<Book> books);

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

