package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating or updating a book review.
 * Client sends only the necessary fields:
 * bookId, customerId, rating, comment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsRequestDTO {

    /** ID of the book being reviewed */
    private Integer bookId;

    /** ID of the customer submitting the review */
    private Integer customerId;

    /** Numeric rating (e.g., 1–5) */
    private int rating;

    /** Optional comment */
    private String comment;
}
