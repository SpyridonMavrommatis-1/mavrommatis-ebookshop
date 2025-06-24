package com.mavrommatis.ebookshop.ebookshop.exception.author;

/**
 * Thrown when no {@code AuthorDetails} entry is found for a given ID.
 * Typically, triggered when querying enriched author metadata.
 */
public class AuthorDetailsNotFoundException extends RuntimeException {
    public AuthorDetailsNotFoundException(Integer id) {
        super("AuthorDetails not found: " + id);
    }
}