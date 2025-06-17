package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for managing the relationship between an author and a book.
 *
 * <p>This DTO models the many-to-many association between
 * {@link AuthorEntity AuthorEntity} and
 * {@link BookEntity BookEntity}
 * through the join entity {@link AuthorBookEntity AuthorBookEntity}.</p>
 *
 * <p>It contains:</p>
 * <ul>
 *   <li>{@code authorId} – the ID of the associated author</li>
 *   <li>{@code bookId} – the ID of the associated book</li>
 *   <li>{@code createdAt} – the timestamp of when the relation was first established</li>
 *   <li>{@code updatedAt} – the timestamp of the last modification</li>
 * </ul>
 *
 * <p>This DTO is used in API responses to safely expose the relationship
 * without leaking internal JPA mappings or entity logic.</p>
 *
 * @see AuthorBookEntity
 * @see AuthorEntity
 * @see BookEntity
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