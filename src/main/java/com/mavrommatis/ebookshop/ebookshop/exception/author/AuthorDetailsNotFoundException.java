package com.mavrommatis.ebookshop.ebookshop.exception.author;

public class AuthorDetailsNotFoundException extends RuntimeException {
    public AuthorDetailsNotFoundException(Integer id) {
        super("AuthorDetails not found: " + id);
    }
}