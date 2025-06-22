package com.mavrommatis.ebookshop.ebookshop.exception.review;

public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(String message) {
        super(message);
    }
}