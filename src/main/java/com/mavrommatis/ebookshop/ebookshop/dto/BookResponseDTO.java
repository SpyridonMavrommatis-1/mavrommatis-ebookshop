package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO used for sending book data to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDTO {

    private int bookId;
    private String title;
    private String language;
    private String genre;
    private String literaryForm;
    private String isbn;
    private boolean isCollective;

    /**
     * Full name of the author (e.g., "George Orwell").
     */
    private String authorName;

    /**
     * The one-to-one details object, if loaded.
     */
    private BookDetailsDTO details;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
