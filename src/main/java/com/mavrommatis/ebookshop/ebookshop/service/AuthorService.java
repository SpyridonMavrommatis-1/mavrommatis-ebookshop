package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorResponseDTO;
import java.util.List;

/**
 * Service interface for managing Author operations via DTOs.
 * <p>
 * Defines methods for CRUD operations that accept and return DTOs,
 * abstracting away persistence details from the client.
 */
public interface AuthorService {

    /**
     * Retrieve all authors.
     *
     * @return list of {@link AuthorResponseDTO}
     */
    List<AuthorResponseDTO> findAll();

    /**
     * Retrieve a specific author by ID.
     *
     * @param id author identifier
     * @return {@link AuthorResponseDTO} of the found author
     * @throws RuntimeException if no author is found
     */
    AuthorResponseDTO findById(Integer id);

    /**
     * Create a new author.
     *
     * @param dto the {@link AuthorRequestDTO} containing author data
     * @return {@link AuthorResponseDTO} of the created author
     */
    AuthorResponseDTO save(AuthorRequestDTO dto);

    /**
     * Update an existing author.
     *
     * @param id  author identifier
     * @param dto the {@link AuthorRequestDTO} containing updated data
     * @return {@link AuthorResponseDTO} of the updated author
     * @throws RuntimeException if no author exists with the given ID
     */
    AuthorResponseDTO update(Integer id, AuthorRequestDTO dto);

    /**
     * Delete an author by ID.
     *
     * @param id author identifier
     * @throws RuntimeException if no author exists with the given ID
     */
    void deleteById(Integer id);

    /**
     * Delete multiple authors by their IDs.
     *
     * @param ids list of author identifiers
     * @throws RuntimeException if any ID is not found
     */
    void deleteAllById(List<Integer> ids);
}
