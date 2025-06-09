package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a Book.
 * Used to transfer book data between layers (e.g., controller ↔ service).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    /**
     * Unique identifier for the book.
     */
    private int bookId;

    /**
     * Title of the book.
     */
    private String title;

    /**
     * Language in which the book is written.
     */
    private String language;

    /**
     * Genre or category of the book.
     */
    private String genre;

    /**
     * Literary form of the book (e.g., Novel, Poem).
     */
    private String literaryForm;

    /**
     * Timestamp for when the book was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp for when the book was last updated.
     */
    private LocalDateTime updatedAt;

    /**
     * ID of the author to which the book belongs.
     */
    private Integer authorId;

    /**
     * Details of the book (e.g., ISBN, page count, description).
     */
    private BookDetailsDTO bookDetails;

    /**
     * List of author-book associations (for many-to-many relationships).
     */
    private List<AuthorBookDTO> authorBooks;
}
