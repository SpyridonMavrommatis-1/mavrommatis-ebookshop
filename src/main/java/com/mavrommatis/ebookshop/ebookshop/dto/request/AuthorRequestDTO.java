package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling client-side author creation or update requests.
 *
 * <p>This object encapsulates user-provided data necessary to create or modify an {@link AuthorEntity AuthorEntity}
 * including optional biographical metadata.</p>
 *
 * <h2>Validation Rules</h2>
 * <ul>
 *   <li>{@code firstName}, {@code lastName} – must be non-blank, 2–50 characters long</li>
 *   <li>{@code email} – must be valid format and unique in the system, enforced via
 *   {@link com.mavrommatis.ebookshop.ebookshop.validator.annotation.UniqueEmail @UniqueEmail}</li>
 *   <li>{@link AuthorDetailsDTO authorDetails} – if provided, is recursively validated via {@code @Valid}</li>
 * </ul>
 *
 * <p>Used exclusively in incoming HTTP requests related to {@code POST} or {@code PUT} operations for authors.</p>
 *
 * @see AuthorEntity
 * @see AuthorDetailsDTO
 * @see com.mavrommatis.ebookshop.ebookshop.validator.annotation.UniqueEmail
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDTO {

    /**
     * Author's first name.
     */
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    /**
     * Author's last name.
     */

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    /**
     * Author's email address.
     */
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    /**
     * Additional profile info of the author (optional during creation).
     */
    @Valid
    private AuthorDetailsDTO authorDetails;
}