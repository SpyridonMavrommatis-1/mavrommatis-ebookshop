package com.mavrommatis.ebookshop.ebookshop.exception.book;

/**
 * Exception thrown when book not found.
 */
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Integer id) {
        super("Book not found: " + id);
    }
}