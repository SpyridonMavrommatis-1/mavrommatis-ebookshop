package com.mavrommatis.ebookshop.ebookshop.exception.customer;


public class CustomerAccessDeniedException extends RuntimeException {
    public CustomerAccessDeniedException(String message) {
        super(message);
    }
}