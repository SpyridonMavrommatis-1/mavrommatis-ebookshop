package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO;

import java.util.List;

/**
 * Service interface for managing the many-to-many relationship between Authors and Books.
 * <p>
 * Defines CRUD operations that accept and return DTOs, abstracting JPA details
 * from the client API.
 * </p>
 */
public interface AuthorBookService {

    /**
     * Retrieve all author-book associations.
     *
     * @return list of {@link AuthorBookResponseDTO} representing all relationships
     */
    List<AuthorBookResponseDTO> findAll();

    /**
     * Retrieve a specific author-book association by composite key.
     *
     * @param authorId the ID of the author
     * @param bookId   the ID of the book
     * @return {@link AuthorBookResponseDTO} of the found association
     * @throws RuntimeException if the association is not found
     */
    AuthorBookResponseDTO findById(Integer authorId, Integer bookId);

}
