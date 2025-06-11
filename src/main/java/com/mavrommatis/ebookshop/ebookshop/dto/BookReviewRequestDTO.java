package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO για δημιουργία/ενημέρωση κριτικής.
 * Ο client στέλνει μόνο ό,τι χρειάζεται:
 * bookId, customerId, rating, comment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookReviewRequestDTO {

    /** ID του βιβλίου που κρίνεται */
    private Integer bookId;

    /** ID του πελάτη που υποβάλλει την κριτική */
    private Integer customerId;

    /** Βαθμολογία (π.χ. 1–5) */
    private int rating;

    /** Προαιρετικό σχόλιο */
    private String comment;
}
