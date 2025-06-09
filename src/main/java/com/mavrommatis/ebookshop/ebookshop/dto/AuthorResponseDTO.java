package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;sddcψ

/**
 * DTO used for sending author information back to the client.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class AuthorResponseDTO {

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