package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookResponseDTO;

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

    /**
     * Create a new author-book association.
     *
     * @param dto the {@link AuthorBookRequestDTO} containing authorId and bookId
     * @return {@link AuthorBookResponseDTO} of the created relationship
     */
    AuthorBookResponseDTO connect(AuthorBookRequestDTO dto);

    /**
     * Create multiple author-book associations in batch.
     * <p>
     * Validates each pair, persists the link only if both Author and Book exist,
     * and returns the list of created associations.
     * </p>
     *
     * @param dtos list of {@link AuthorBookRequestDTO} to create
     * @return list of {@link AuthorBookResponseDTO} of the created relationships
     * @throws RuntimeException if any referenced Author or Book does not exist
     */
    List<AuthorBookResponseDTO> connectAll(List<AuthorBookRequestDTO> dtos);

    /**
     * Delete an existing author-book association.
     *
     * @param authorId the ID of the author
     * @param bookId   the ID of the book
     * @throws RuntimeException if the association does not exist
     */
    void deleteById(Integer authorId, Integer bookId);

    /**
     * Delete multiple author-book associations.
     *
     * @param dtos list of {@link AuthorBookRequestDTO} to delete
     * @throws RuntimeException if any association does not exist
     */
    void deleteAll(List<AuthorBookRequestDTO> dtos);
}
