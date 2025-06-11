package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

/**
 * DTO containing extended profile information for an author.
 * Used when retrieving or displaying detailed author metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetailsDTO {

    /**
     * Short biography of the author.
     */
    private String biography;

    /**
     * Date of birth.
     */
    private LocalDate birthDate;

    /**
     * Personal website link of the author.
     */
    private String website;
}