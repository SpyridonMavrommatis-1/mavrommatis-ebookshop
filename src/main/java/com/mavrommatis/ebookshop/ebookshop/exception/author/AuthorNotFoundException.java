package com.mavrommatis.ebookshop.ebookshop.exception.author;

/**
 * Thrown when an {@code Author} entity with the specified ID is not found.
 */
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Integer id) {
        super("Author not found: " + id);
    }
}