package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsResponseDTO;

import java.util.List;

/**
 * Service interface for managing book reviews via DTOs.
 * <p>
 * Defines CRUD operations that accept BookReviewRequestDTO and return
 * BookReviewResponseDTO, abstracting persistence from client code.
 */
public interface BookReviewsService {

    /**
     * Retrieve all book reviews.
     *
     * @return list of {@link BookReviewsResponseDTO}
     */
    List<BookReviewsResponseDTO> findAll();

    /**
     * Retrieve a single book review by its ID.
     *
     * @param id the unique identifier of the review
     * @return the corresponding {@link BookReviewsResponseDTO}
     * @throws RuntimeException if not found
     */
    BookReviewsResponseDTO findById(Integer id);

    /**
     * Create a new book review.
     *
     * @param dto the {@link BookReviewsRequestDTO} containing review data
     * @return the created {@link BookReviewsResponseDTO}
     */
    BookReviewsResponseDTO save(BookReviewsRequestDTO dto);

    /**
     * Update an existing book review.
     *
     * @param id  the ID of the review to update
     * @param dto the {@link BookReviewsRequestDTO} containing updated data
     * @return the updated {@link BookReviewsResponseDTO}
     * @throws RuntimeException if not found
     */
    BookReviewsResponseDTO update(Integer id, BookReviewsRequestDTO dto);

    /**
     * Delete a book review by its ID.
     *
     * @param id the ID of the review to delete
     * @throws RuntimeException if not found
     */
    void deleteById(Integer id);

    /**
     * Create multiple new book reviews in batch.
     * <p>
     * Validates that each (bookId, customerId) pair is unique before saving.
     *
     * @param dtos list of review DTOs to create
     * @return list of created {@link BookReviewsResponseDTO}
     * @throws RuntimeException if any review already exists
     */
    List<BookReviewsResponseDTO> saveAll(List<BookReviewsRequestDTO> dtos);

    /**
     * Delete multiple book reviews by their IDs.
     *
     * @param ids list of review IDs to delete
     * @throws RuntimeException if any ID is not found
     */
    void deleteAllById(List<Integer> ids);
}