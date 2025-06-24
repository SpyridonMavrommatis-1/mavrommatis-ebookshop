package com.mavrommatis.ebookshop.ebookshop.exception.review;

import org.springframework.security.access.AccessDeniedException;

/**
 * Thrown when a user tries to modify or delete a review they do not own.
 * Extends Spring Security's {@link AccessDeniedException} for compatibility.
 */
public class UnauthorizedReviewActionException extends AccessDeniedException {
    public UnauthorizedReviewActionException(String message) {
        super(message);
    }
}
