package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing book data during client requests.
 *
 * <p>This DTO is used for HTTP operations that manage
 * {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity BookEntity}.
 * It encapsulates all relevant book metadata sent from the client,
 * including optional one-to-one book details.</p>
 *
 * <p>It does <strong>not</strong> include fields like {@code bookId}, timestamps,
 * or author names, since those are either auto-generated or derived on the server side.</p>
 *
 * <p>The {@code authorId} field serves as a reference to associate the book with an
 * existing {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity AuthorEntity}.</p>
 *
 * @see com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity
 * @see com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO
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

    /**
     * If present, the BookDetails to create/update in the same call.
     */
    private BookDetailsDTO details;
}