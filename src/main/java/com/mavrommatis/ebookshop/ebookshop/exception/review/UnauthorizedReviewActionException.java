package com.mavrommatis.ebookshop.ebookshop.exception.review;

import org.springframework.security.access.AccessDeniedException;

public class UnauthorizedReviewActionException extends AccessDeniedException {
    public UnauthorizedReviewActionException(String message) {
        super(message);
    }
}