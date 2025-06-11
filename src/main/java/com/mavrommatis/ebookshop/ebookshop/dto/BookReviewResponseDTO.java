package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO για την απόκριση με τα δεδομένα κριτικής.
 * Επιστρέφει reviewId, bookId, customerId, rating, comment, createdAt, updatedAt.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewResponseDTO {

    /** Unique ID της κριτικής */
    private Integer reviewId;

    /** ID του βιβλίου */
    private Integer bookId;

    /** ID του πελάτη */
    private Integer customerId;

    /** Rating (υποχρεωτικό) */
    private int rating;

    /** Προαιρετικό σχόλιο */
    private String comment;

    /** Πότε δημιουργήθηκε */
    private LocalDateTime createdAt;

    /** Πότε τροποποιήθηκε τελευταία */
    private LocalDateTime updatedAt;
}
