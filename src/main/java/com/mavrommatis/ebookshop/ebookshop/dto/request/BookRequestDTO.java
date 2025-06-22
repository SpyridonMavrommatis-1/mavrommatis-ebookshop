package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidISBN;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Language is required")
    @Size(max = 50, message = "Language must be at most 50 characters")
    private String language;

    @NotBlank(message = "Genre is required")
    @Size(max = 50, message = "Genre must be at most 50 characters")
    private String genre;

    @NotBlank(message = "Literary form is required")
    @Size(max = 50, message = "Literary form must be at most 50 characters")
    private String literaryForm;

    @NotBlank(message = "ISBN is required")
    @ValidISBN
    private String isbn;

    private boolean isCollective;

    /**
     * The ID of the author who wrote the book. Used for look-up.
     */
    @NotNull(message = "Author ID is required")
    private Integer authorId;

    /**
     * If present, the BookDetails to create/update in the same call.
     */
    @Valid
    private BookDetailsDTO details;
}