package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for transferring book detail information
 * from and to the client (e.g., API request/response).
 * <p>
 * This DTO corresponds to {@link com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity}
 * and is used independently of the Book entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailsDTO {

    /**
     * Date the book was published.
     */
    private LocalDate publishDate;

    /**
     * Total number of pages in the book.
     */
    private int pages;

    /**
     * Brief summary or description of the book's content.
     */
    private String summary;

    /**
     * Dimensions of the book (e.g., "21 x 14 x 2 cm").
     */
    private String dimensions;

    /**
     * Type of book cover (e.g., "Hardcover", "Paperback").
     */
    private String coverType;

    /**
     * Weight of the book in kilograms.
     */
    private BigDecimal weight;
}