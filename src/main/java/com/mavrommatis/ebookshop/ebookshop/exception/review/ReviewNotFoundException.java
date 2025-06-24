package com.mavrommatis.ebookshop.ebookshop.exception.review;

/**
 * Thrown when a {@code BookReview} is not found by ID.
 */
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
