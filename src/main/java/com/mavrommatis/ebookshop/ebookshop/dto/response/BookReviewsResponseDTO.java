package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for sending book review information to clients.
 *
 * <p>This DTO represents a flattened structure of a
 * {@link BookReviewsEntity}
 * and includes key review attributes along with references to the associated book and customer.</p>
 *
 * <p>It encapsulates:</p>
 * <ul>
 *   <li>{@code reviewId} – unique identifier of the review</li>
 *   <li>{@code bookId} – the ID of the reviewed book</li>
 *   <li>{@code customerId} – the ID of the reviewer</li>
 *   <li>{@code rating} – numeric score representing the customer's evaluation</li>
 *   <li>{@code comment} – optional text feedback</li>
 *   <li>{@code createdAt}, {@code updatedAt} – timestamps for lifecycle tracking</li>
 * </ul>
 *
 * <p>This DTO is used in API responses to expose review data in a client-safe format.</p>
 *
 * @see BookReviewsEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewsResponseDTO {

    /** Unique identifier of the review */
    private Integer reviewId;

    /** ID of the book */
    private Integer bookId;

    /** ID of the customer */
    private Integer customerId;

    /** Numeric rating (required) */
    private int rating;

    /** Optional comment */
    private String comment;

    /** Timestamp when the review was created */
    private LocalDateTime createdAt;

    /** Timestamp when the review was last updated */
    private LocalDateTime updatedAt;
}
