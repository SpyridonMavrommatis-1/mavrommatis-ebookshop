package com.mavrommatis.ebookshop.ebookshop.exception.review;

/**
 * Thrown when a customer attempts to submit a review that already exists.
 * Typically enforced at the service layer to prevent duplicates.
 */
public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(String message) {
        super(message);
    }
}
