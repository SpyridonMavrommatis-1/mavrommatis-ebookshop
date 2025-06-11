package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.BookDetailsDTO;

import java.util.List;

/**
 * Service interface for managing detailed book metadata via DTOs.
 */
public interface BookDetailsService {

    /**
     * Retrieve all book details.
     * @return list of {@link BookDetailsDTO}
     */
    List<BookDetailsDTO> findAll();

    /**
     * Retrieve a specific book details record.
     * @param id the book ID
     * @return the {@link BookDetailsDTO}
     * @throws RuntimeException if not found
     */
    BookDetailsDTO findById(Integer id);

    /**
     * Create or update book details.
     * @param dto the {@link BookDetailsDTO} to save
     * @return the saved {@link BookDetailsDTO}
     */
    BookDetailsDTO save(BookDetailsDTO dto);

    /**
     * Create or update multiple book details at once.
     * @param dtos list of {@link BookDetailsDTO}
     * @return list of saved {@link BookDetailsDTO}
     */
    List<BookDetailsDTO> saveAll(List<BookDetailsDTO> dtos);

    /**
     * Delete book details by book ID.
     * @param id the book ID
     * @throws RuntimeException if not found
     */
    void deleteById(Integer id);

    /**
     * Delete multiple book details by book IDs.
     * @param ids list of book IDs
     */
    void deleteAllById(List<Integer> ids);
}
