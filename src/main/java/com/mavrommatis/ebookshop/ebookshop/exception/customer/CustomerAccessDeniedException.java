package com.mavrommatis.ebookshop.ebookshop.exception.customer;


/**
 * Raised when access is denied to a customer-specific operation.
 * Often used to enforce ownership or role-based restrictions.
 */
public class CustomerAccessDeniedException extends RuntimeException {
    public CustomerAccessDeniedException(String message) {
        super(message);
    }
}