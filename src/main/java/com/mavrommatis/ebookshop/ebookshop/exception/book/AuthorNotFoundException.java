package com.mavrommatis.ebookshop.ebookshop.exception.book;

/**
 * Exception thrown when author not found.
 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Integer id) {
        super("Author not found: " + id);
    }
}