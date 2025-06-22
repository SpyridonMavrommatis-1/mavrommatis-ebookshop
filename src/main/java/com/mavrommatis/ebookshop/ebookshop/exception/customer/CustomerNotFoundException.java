package com.mavrommatis.ebookshop.ebookshop.exception.customer;

/**
 * Thrown when a customer with the specified ID is not found in the database.
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer id) {
        super("Customer not found: " + id);
    }

    public CustomerNotFoundException(String username) {
        super("Customer not found with username: " + username);
    }
}