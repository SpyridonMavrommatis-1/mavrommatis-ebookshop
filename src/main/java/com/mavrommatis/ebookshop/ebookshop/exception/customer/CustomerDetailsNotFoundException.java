package com.mavrommatis.ebookshop.ebookshop.exception.customer;

/**
 * Thrown when customer details are not found for a given customer ID.
 */
public class CustomerDetailsNotFoundException extends RuntimeException {
    public CustomerDetailsNotFoundException(Integer customerId) {
        super("CustomerDetails not found: " + customerId);
    }
}