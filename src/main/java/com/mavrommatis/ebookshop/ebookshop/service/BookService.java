package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookResponseDTO;
import java.util.List;

/**
 * Service interface for managing Book operations via DTOs.
 * <p>
 * Defines methods for CRUD operations that accept and return DTOs,
 * abstracting away persistence details from the client.
 */
public interface BookService {

    /**
     * Retrieve all books from the database.
     *
     * @return a list of {@link BookResponseDTO} representing all books
     */
    List<BookResponseDTO> findAll();

    /**
     * Retrieve a specific book by its ID.
     *
     * @param id the unique identifier of the book
     * @return a {@link BookResponseDTO} of the found book
     * @throws RuntimeException if no book is found with the given ID
     */
    BookResponseDTO findById(Integer id);

    /**
     * Create a new book in the system.
     *
     * @param dto the {@link BookRequestDTO} containing book data
     * @return a {@link BookResponseDTO} of the created book
     */
    BookResponseDTO save(BookRequestDTO dto);

    /**
     * Update an existing book.
     *
     * @param id  the ID of the book to update
     * @param dto the {@link BookRequestDTO} containing updated book data
     * @return a {@link BookResponseDTO} of the updated book
     * @throws RuntimeException if no book exists with the given ID
     */
    BookResponseDTO update(Integer id, BookRequestDTO dto);

    /**
     * Delete a book by its ID.
     *
     * @param id the ID of the book to delete
     * @throws RuntimeException if no book exists with the given ID
     */
    void deleteById(Integer id);

    /**
     * Delete multiple books by their IDs.
     *
     * @param ids a list of book IDs to delete
     * @throws RuntimeException if any book ID is not found
     */
    void deleteAllById(List<Integer> ids);
}