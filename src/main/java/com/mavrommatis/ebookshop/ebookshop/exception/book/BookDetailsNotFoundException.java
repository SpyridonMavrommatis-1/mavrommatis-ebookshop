package com.mavrommatis.ebookshop.ebookshop.exception.book;

/**
 * Exception thrown when bookdetails not found.
 */
public class BookDetailsNotFoundException extends RuntimeException {
    public BookDetailsNotFoundException(Integer id) {
        super("BookDetails not found: " + id);
    }
}