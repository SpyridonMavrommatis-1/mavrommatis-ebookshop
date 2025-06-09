package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for receiving data when creating or updating an author.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AuthorRequestDTO {

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
