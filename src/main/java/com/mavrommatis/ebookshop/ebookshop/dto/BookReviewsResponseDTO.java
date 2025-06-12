package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for returning book review data in responses.
 * Contains reviewId, bookId, customerId, rating, comment, createdAt, and updatedAt.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsResponseDTO {

    /** Unique identifier of the review */
    private Integer reviewId;

    /** ID of the book */
    private Integer bookId;

    /** ID of the customer */
    private Integer customerId;

    /** Numeric rating (required) */
    private int rating;

    /** Optional comment */
    private String comment;

    /** Timestamp when the review was created */
    private LocalDateTime createdAt;

    /** Timestamp when the review was last updated */
    private LocalDateTime updatedAt;
}
