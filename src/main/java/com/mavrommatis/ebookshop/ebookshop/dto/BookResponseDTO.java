package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) used for sending book data to the client.
 * <p>
 * This class is typically used in API responses. The {@code authorName} field
 * provides a user-friendly way of identifying the author, by exposing their
 * full name (i.e., first name and last name) instead of their internal ID.
 * <p>
 * The full name is composed in the Mapper layer by combining the
 * {@code getFirstName()} and {@code getLastName()} methods of the
 * related {@code Author} entity.
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
     * <p>
     * This field is populated in the Mapper layer from the
     * author's first and last name.
     */
    private String authorName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
