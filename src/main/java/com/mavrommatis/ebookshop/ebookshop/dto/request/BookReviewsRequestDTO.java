package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing book review submissions from clients.
 *
 * <p>This DTO captures the core data required to submit or update a review for a book.</p>
 *
 * <ul>
 *   <li>{@code bookId} – the identifier of the reviewed
 *   {@link BookEntity Book}</li>
 *   <li>{@code customerId} – the identifier of the reviewing
 *   {@link CustomerEntity Customer}</li>
 *   <li>{@code rating} – numeric rating, typically from 1 to 5</li>
 *   <li>{@code comment} – optional free-form text comment</li>
 * </ul>
 *
 * <p>This object is used in HTTP request bodies to manage entries in
 * {@link BookReviewsEntity BookReviewEntity}.</p>
 *
 * @see BookReviewsEntity
 * @see BookEntity
 * @see CustomerEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsRequestDTO {

    /** ID of the book being reviewed */
    private Integer bookId;

    /** ID of the customer submitting the review */
    private Integer customerId;

    /** Numeric rating (e.g., 1–5) */
    private int rating;

    /** Optional comment */
    private String comment;
}
