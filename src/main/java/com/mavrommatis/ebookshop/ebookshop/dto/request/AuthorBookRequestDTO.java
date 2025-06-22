package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing relationship between an author and a book.
 *
 * <p>This DTO represents the input data required to associate an
 * {@link AuthorEntity AuthorEntity}
 * with a {@link BookEntity BookEntity}
 * using their respective IDs.</p>
 *
 * <p>Only the composite key is provided by the client. Timestamps and internal metadata
 * are managed automatically by the server.</p>
 *
 * <p>Used in HTTP REQUESTS operations that manage many-to-many relationships
 * via the {@link AuthorBookEntity AuthorBookEntity} join table.</p>
 *
 * @see AuthorEntity
 * @see BookEntity
 * @see AuthorBookEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorBookRequestDTO {

    /** The unique identifier of the author. */
    @NotNull(message = "Author ID is required")
    @Positive(message = "Author ID must be a positive integer")
    private Integer authorId;

    /** The unique identifier of the book. */
    @NotNull(message = "Book ID is required")
    @Positive(message = "Book ID must be a positive integer")
    private Integer bookId;
}
