package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used when creating or updating a book.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestDTO {

    private String title;
    private String language;
    private String genre;
    private String literaryForm;
    private String isbn;
    private boolean isCollective;

    /**
     * The ID of the author who wrote the book. Used for look-up.
     */
    private Integer authorId;
}