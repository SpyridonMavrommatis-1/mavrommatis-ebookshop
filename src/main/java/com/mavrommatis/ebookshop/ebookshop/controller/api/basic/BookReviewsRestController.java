package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.BookReviewsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * <p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} roles.
     * </p>
     * @return a list of BookReviewResponseDTO
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<BookReviewsResponseDTO> getAll() {
        return bookReviewService.findAll();
    }

    /**
     * Return a single book review by its ID.
     * <p>
     *   Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} roles.
     * </p>
     * @param reviewsId the review’s ID
     * @return the matching BookReviewResponseDTO
     */
    @GetMapping("/{reviewsId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public BookReviewsResponseDTO getById(@PathVariable Integer reviewsId) {
        return bookReviewService.findById(reviewsId);
    }

    /**
     * Create a new book review.
     * <p>
     *   Accessible to {@code CUSTOMER}
     * </p>
     * @param request the review data (bookId, customerId, rating, comment)
     * @return the created BookReviewResponseDTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public BookReviewsResponseDTO create(@RequestBody @Valid BookReviewsRequestDTO request) {
        return bookReviewService.save(request);
    }

    /**
     * Update an existing book review.
     * <p>
     *    Accessible to {@code CUSTOMER}
     * </p>
     * @param reviewsId  the ID of the review to update
     * @param request the new review data
     * @return the updated BookReviewResponseDTO
     */
    @PutMapping("/{reviewsId}")
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public BookReviewsResponseDTO update(
            @PathVariable Integer reviewsId,
            @RequestBody @Valid BookReviewsRequestDTO request
    ) {
        return bookReviewService.update(reviewsId, request);
    }

    /**
     * Delete a book review by its ID.
     * <p>
     *    Accessible to {@code CUSTOMER}
     * </p>
     * @param reviewsId the ID of the review to delete
     */
    @DeleteMapping("/{reviewsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public void delete(@PathVariable Integer reviewsId) {
        bookReviewService.deleteById(reviewsId);
    }



    /**
     * Delete multiple book reviews in batch.
     * <p>
     *    Accessible to {@code ADMIN} role.
     * </p>
     * @param ids list of review IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBatch(@RequestBody List<Integer> ids) {
        bookReviewService.deleteAllById(ids);
    }
}
