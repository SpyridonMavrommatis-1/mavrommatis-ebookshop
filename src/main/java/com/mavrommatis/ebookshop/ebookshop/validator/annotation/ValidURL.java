package com.mavrommatis.ebookshop.ebookshop.validator.annotation;

import com.mavrommatis.ebookshop.ebookshop.validator.validation.ValidURLValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
/**
 * Custom validation annotation to ensure a given string represents a valid HTTP or HTTPS URL.
 *
 * <p>This annotation triggers the {@link ValidURLValidator} class, which internally uses {@link java.net.URL}
 * for structural validation and additional checks specific to HTTP(S) protocol semantics.</p>
 *
 * <p>Validation criteria include:</p>
 * <ul>
 *   <li>Properly formed URL syntax (scheme://host...)</li>
 *   <li>Allowed protocol: only <b>http</b> or <b>https</b></li>
 *   <li>Host must include a domain component (e.g., "example.com")</li>
 * </ul>
 *
 * <p>Intended for use on fields like personal websites, publisher links, and other web resources.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 *     @ValidURL
 *     private String website;
 * }</pre>
 *
 * @see ValidURLValidator
 */
@Documented
@Constraint(validatedBy = ValidURLValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidURL {
    /**
     * Message that will be shown when the validation fails.
     * Default: "Invalid website URL"
     */
    String message() default "Invalid website URL";

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

