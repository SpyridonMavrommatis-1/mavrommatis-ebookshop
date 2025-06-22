package com.mavrommatis.ebookshop.ebookshop.dto.details;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing detailed metadata of a book.
 *
 * <p>This DTO is used in both incoming {@code request} bodies and outgoing {@code response} payloads
 * whenever extended book information is involved.</p>
 *
 * <p>It corresponds to the {@link BookDetailsEntity BookDetailsEntity}
 * and is mapped via a one-to-one relationship with
 * {@link BookEntity BookEntity}.</p>
 *
 * <p>This DTO includes physical and editorial information such as:</p>
 * <ul>
 *   <li>Publication date, number of pages, and summary</li>
 *   <li>Book dimensions, cover type, and weight</li>
 * </ul>
 *
 * @see BookDetailsEntity
 * @see BookEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailsDTO {

    /**
     * Date the book was published.
     */
    @PastOrPresent(message = "Publish date must be in the past or present")
    private LocalDate publishDate;

    /**
     * Total number of pages in the book.
     */
    @Min(value = 1, message = "Page count must be at least 1")
    @Max(value = 10000, message = "Page count must not exceed 10,000")
    private int pages;

    /**
     * Brief summary or description of the book's content.
     */
    @Size(max = 2000, message = "Summary cannot exceed 2000 characters")
    private String summary;

    /**
     * Dimensions of the book (e.g., "21 x 14 x 2 cm").
     */
    @Size(max = 100, message = "Dimensions must not exceed 100 characters")
    private String dimensions;

    /**
     * Type of book cover (e.g., "Hardcover", "Paperback").
     */

    @Size(max = 50, message = "Cover type must not exceed 50 characters")
    private String coverType;

    /**
     * Weight of the book in kilograms.
     */
    @DecimalMin(value = "0.0", inclusive = true, message = "Weight must be non-negative")
    @Digits(integer = 2, fraction = 2, message = "Weight must be a valid decimal (max 2 decimal places)")
    private BigDecimal weight;
}