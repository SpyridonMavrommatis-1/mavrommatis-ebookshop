package com.mavrommatis.ebookshop.ebookshop.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing book review submissions from clients.
 *
 * <p>This DTO captures the core data required to submit or update a review for a book.</p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsRequestDTO {

    /**
     * ID of the book being reviewed. Must be provided.
     */
    @NotNull(message = "Book ID is required")
    private Integer bookId;

    /**
     * Numeric rating between 1 and 5.
     */
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must not exceed 5")
    private int rating;

    /**
     * Optional review comment. Limited to 1000 characters.
     */
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String comment;
}
