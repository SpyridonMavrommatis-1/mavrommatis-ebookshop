package com.mavrommatis.ebookshop.ebookshop.dto.details;

import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
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