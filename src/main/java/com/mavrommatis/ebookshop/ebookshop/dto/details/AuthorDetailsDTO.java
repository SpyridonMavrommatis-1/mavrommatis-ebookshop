package com.mavrommatis.ebookshop.ebookshop.dto.details;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidURL;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) containing extended profile information for an author.
 *
 * <p>This DTO encapsulates optional biographical metadata associated with an
 * {@link AuthorEntity AuthorEntity}.</p>
 *
 * <p>It is used in both API requests and responses when detailed author information
 * is included, typically through a one-to-one relationship with the core entity.</p>
 *
 * <p>The data includes:</p>
 * <ul>
 *   <li>{@code biography} — a short textual bio of the author</li>
 *   <li>{@code birthDate} — date of birth</li>
 *   <li>{@code website} — personal or professional website link</li>
 * </ul>
 *
 * @see AuthorEntity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetailsDTO {

    /**
     * Short biography of the author.
     */
    @Size(max = 1000, message = "Biography cannot exceed 1000 characters")
    private String biography;

    /**
     * Date of birth.
     */
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    /**
     * Personal website link of the author.
     */
    @ValidURL
    private String website;
}