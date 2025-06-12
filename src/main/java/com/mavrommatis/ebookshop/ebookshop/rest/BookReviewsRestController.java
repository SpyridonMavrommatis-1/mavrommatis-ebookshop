package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.BookReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book reviews.
 * <p>
 * All endpoints accept and return DTOs to keep the API layer
 * independent of JPA entity internals.
 * </p>
 */
@RestController
@RequestMapping("/api/book-reviews")
public class BookReviewsRestController {

    private final BookReviewsService bookReviewService;

    @Autowired
    public BookReviewsRestController(BookReviewsService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    /**
     * Return all book reviews.
     *
     * @return a list of BookReviewResponseDTO
     */
    @GetMapping
    public List<BookReviewsResponseDTO> getAll() {
        return bookReviewService.findAll();
    }

    /**
     * Return a single book review by its ID.
     *
     * @param reviewsId the review’s ID
     * @return the matching BookReviewResponseDTO
     */
    @GetMapping("/{reviewsId}")
    public BookReviewsResponseDTO getById(@PathVariable Integer reviewsId) {
        return bookReviewService.findById(reviewsId);
    }

    /**
     * Create a new book review.
     *
     * @param request the review data (bookId, customerId, rating, comment)
     * @return the created BookReviewResponseDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookReviewsResponseDTO create(@RequestBody BookReviewsRequestDTO request) {
        return bookReviewService.save(request);
    }

    /**
     * Update an existing book review.
     *
     * @param reviewsId  the ID of the review to update
     * @param request the new review data
     * @return the updated BookReviewResponseDTO
     */
    @PutMapping("/{reviewsId}")
    public BookReviewsResponseDTO update(
            @PathVariable Integer reviewsId,
            @RequestBody BookReviewsRequestDTO request
    ) {
        return bookReviewService.update(reviewsId, request);
    }

    /**
     * Delete a book review by its ID.
     *
     * @param reviewsId the ID of the review to delete
     */
    @DeleteMapping("/{reviewsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer reviewsId) {
        bookReviewService.deleteById(reviewsId);
    }

    /**
     * Create multiple new book reviews in batch.
     *
     * @param requests list of review DTOs to create
     * @return list of created {@link BookReviewsResponseDTO}
     * @throws RuntimeException if any review violates the unique constraint
     */
    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BookReviewsResponseDTO> createBatch(
            @RequestBody List<BookReviewsRequestDTO> requests) {
        return bookReviewService.saveAll(requests);
    }

    /**
     * Delete multiple book reviews in batch.
     *
     * @param ids list of review IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@RequestBody List<Integer> ids) {
        bookReviewService.deleteAllById(ids);
    }
}
