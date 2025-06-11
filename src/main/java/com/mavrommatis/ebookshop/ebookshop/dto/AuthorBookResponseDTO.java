package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing the many-to-many relationship
 * between an Author and a Book.
 * <p>
 * This DTO encapsulates the composite key (authorId, bookId) as well as
 * metadata such as creation and update timestamps.
 * <p>
 * It is used to transfer author-book relationship data between the application
 * layers without exposing the internal JPA entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorBookResponseDTO {

    /**
     * The unique identifier of the author.
     */
    private Integer authorId;

    /**
     * The unique identifier of the book.
     */
    private Integer bookId;

    /**
     * Timestamp indicating when this relationship was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when this relationship was last updated.
     */
    private LocalDateTime updatedAt;
}