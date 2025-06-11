package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewResponseDTO;
import java.util.List;

/**
 * Service interface for managing book reviews via DTOs.
 * <p>
 * Defines CRUD operations that accept BookReviewRequestDTO and return
 * BookReviewResponseDTO, abstracting persistence from client code.
 */
public interface BookReviewService {

    /**
     * Retrieve all book reviews.
     *
     * @return list of {@link BookReviewResponseDTO}
     */
    List<BookReviewResponseDTO> findAll();

    /**
     * Retrieve a single book review by its ID.
     *
     * @param id the unique identifier of the review
     * @return the corresponding {@link BookReviewResponseDTO}
     * @throws RuntimeException if not found
     */
    BookReviewResponseDTO findById(Integer id);

    /**
     * Create a new book review.
     *
     * @param dto the {@link BookReviewRequestDTO} containing review data
     * @return the created {@link BookReviewResponseDTO}
     */
    BookReviewResponseDTO save(BookReviewRequestDTO dto);

    /**
     * Update an existing book review.
     *
     * @param id  the ID of the review to update
     * @param dto the {@link BookReviewRequestDTO} containing updated data
     * @return the updated {@link BookReviewResponseDTO}
     * @throws RuntimeException if not found
     */
    BookReviewResponseDTO update(Integer id, BookReviewRequestDTO dto);

    /**
     * Delete a book review by its ID.
     *
     * @param id the ID of the review to delete
     * @throws RuntimeException if not found
     */
    void deleteById(Integer id);

    /**
     * Delete multiple book reviews by their IDs.
     *
     * @param ids list of review IDs to delete
     * @throws RuntimeException if any ID is not found
     */
    void deleteAllById(List<Integer> ids);
}