package com.mavrommatis.ebookshop.ebookshop.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for sending author information to clients.
 *
 * <p>This DTO provides a simplified and flattened representation of
 * {@link AuthorEntity},
 * typically used in API responses or MVC views.</p>
 *
 * <p>It encapsulates key metadata about the author including:</p>
 * <ul>
 *   <li>{@code firstName}, {@code lastName} – personal identity of the author</li>
 *   <li>{@code email} – contact information</li>
 *   <li>{@link AuthorDetailsDTO} – optional nested metadata such as bio, birthdate, and more</li>
 * </ul>
 *
 * <p>Timestamps such as {@code createdAt} and {@code updatedAt} reflect entity lifecycle moments.</p>
 *
 * <p><strong>Note:</strong> This DTO is exclusively used for output purposes. It is
 * populated by the server and is <em>not subject to validation</em> since
 * it is never received as input from client requests.</p>
 *
 * @see AuthorEntity
 * @see AuthorDetailsDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorResponseDTO {

    /**
     * Unique identifier of the author.
     */
    private int authorId;

    /**
     * Author's first name.
     */
    private String firstName;

    /**
     * Author's last name.
     */
    private String lastName;

    /**
     * Author's email address.
     */
    private String email;

    /**
     * Timestamp of when the author was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp of last update.
     */
    private LocalDateTime updatedAt;

    /**
     * Associated details of the author, such as bio and birth date.
     */
    private AuthorDetailsDTO authorDetails;
}
