package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used when creating an association between an Author and a Book.
 * Only the composite key fields are needed — timestamps are handled by the server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorBookRequestDTO {

    /** The unique identifier of the author. */
    private Integer authorId;

    /** The unique identifier of the book. */
    private Integer bookId;
}
