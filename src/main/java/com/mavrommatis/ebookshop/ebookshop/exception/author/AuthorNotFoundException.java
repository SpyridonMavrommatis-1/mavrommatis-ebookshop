package com.mavrommatis.ebookshop.ebookshop.exception.author;

public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Integer id) {
        super("Author not found: " + id);
    }
}