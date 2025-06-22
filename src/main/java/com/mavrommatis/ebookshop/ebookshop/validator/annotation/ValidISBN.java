package com.mavrommatis.ebookshop.ebookshop.validator.annotation;

import com.mavrommatis.ebookshop.ebookshop.validator.validation.ValidISBNValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure a given string is a valid ISBN-10 or ISBN-13.
 *
 * <p>This annotation triggers the {@link ValidISBNValidator} which performs:
 * <ul>
 *     <li>Format check (ISBN-10 or ISBN-13)</li>
 *     <li>Checksum validation for data integrity</li>
 * </ul>
 *
 * <p>Supports both hyphenated and non-hyphenated inputs and ignores whitespace.</p>
 *
 * <p>Usage:</p>
 * <pre>
 *     {@code @ValidISBN}
 *     private String isbn;
 * </pre>
 *
 * @see ValidISBNValidator
 */
@Documented
@Constraint(validatedBy = ValidISBNValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidISBN {
    /**
     * Message that will be shown when the validation fails.
     * Default: "Invalid ISBN (must be valid ISBN-10 or ISBN-13 with correct checksum)"
     */
    String message() default "Invalid ISBN (must be valid ISBN-10 or ISBN-13 with correct checksum)";


    /**
     * Validation groups — used for grouping validations. Usually left empty.
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients of the Bean Validation API to assign custom payload objects to a constraint.
     * Typically unused.
     */
    Class<? extends Payload>[] payload() default {};
}
