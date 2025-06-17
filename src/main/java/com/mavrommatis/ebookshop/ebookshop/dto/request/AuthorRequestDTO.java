package com.mavrommatis.ebookshop.ebookshop.dto.request;

import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for managing author data via client requests.
 *
 * <p>This DTO captures the author's personal and optional extended details,
 * which are typically handled through create, update, or delete operations.</p>
 *
 * <p>It includes:</p>
 * <ul>
 *   <li>{@code firstName} and {@code lastName} — the author's full name</li>
 *   <li>{@code email} — the author's contact email address</li>
 *   <li>{@link AuthorDetailsDTO authorDetails} — optional nested metadata (e.g., bio, birth date)</li>
 * </ul>
 *
 * <p>Used in  HTTP REQUESTS operations that manage
 * {@link AuthorEntity AuthorEntity} instances.</p>
 *
 * @see AuthorEntity
 * @see AuthorDetailsDTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorRequestDTO {

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
     * Additional profile info of the author (optional during creation).
     */
    private AuthorDetailsDTO authorDetails;
}