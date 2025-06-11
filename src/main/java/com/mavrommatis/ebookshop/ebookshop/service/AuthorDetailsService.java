package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorDetailsDTO;
import java.util.List;

/**
 * Service interface for managing AuthorDetails via DTOs.
 * <p>
 * Hides persistence and entity details from the client layer.
 */
public interface AuthorDetailsService {

    /**
     * Retrieve all author‐detail records.
     *
     * @return a list of {@link AuthorDetailsDTO}
     */
    List<AuthorDetailsDTO> findAll();

    /**
     * Retrieve a single author‐detail record by author ID.
     *
     * @param authorId the author's ID
     * @return the corresponding {@link AuthorDetailsDTO}
     * @throws RuntimeException if not found
     */
    AuthorDetailsDTO findById(Integer authorId);

    /**
     * Create or update an author‐detail record.
     * <p>
     * If a record with the same authorId exists, it will be updated; otherwise a new one is inserted.
     * </p>
     *
     * @param dto the {@link AuthorDetailsDTO} to save
     * @return the saved {@link AuthorDetailsDTO}
     */
    AuthorDetailsDTO save(AuthorDetailsDTO dto);

    /**
     * Batch create or update multiple author‐detail records.
     *
     * @param dtos list of {@link AuthorDetailsDTO} to persist
     * @return list of persisted {@link AuthorDetailsDTO}
     */
    List<AuthorDetailsDTO> saveAll(List<AuthorDetailsDTO> dtos);

    /**
     * Delete an author‐detail record by author ID.
     *
     * @param authorId the author's ID
     * @throws RuntimeException if no such record exists
     */
    void deleteById(Integer authorId);

    /**
     * Delete multiple author‐detail records by their author IDs.
     *
     * @param authorIds list of author IDs to delete
     * @throws RuntimeException if any ID is not found
     */
    void deleteAllById(List<Integer> authorIds);
}
