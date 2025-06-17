package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for sending book information to clients via API responses.
 *
 * <p>This DTO provides a flattened and simplified representation of a
 * {@link BookEntity}
 * along with select author and detail information.</p>
 *
 * <p>It encapsulates metadata like title, language, genre, and also references to:</p>
 * <ul>
 *   <li>{@code authorName} - the full name of the book's primary author</li>
 *   <li>{@link BookDetailsDTO} - optional detailed metadata for the book (e.g., description, pages, etc.)</li>
 * </ul>
 *
 * @see BookEntity
 * @see BookDetailsDTO
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
