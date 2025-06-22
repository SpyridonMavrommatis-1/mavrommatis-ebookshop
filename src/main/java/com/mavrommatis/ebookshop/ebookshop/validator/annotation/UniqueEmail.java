package com.mavrommatis.ebookshop.ebookshop.validator.annotation;

import com.mavrommatis.ebookshop.ebookshop.validator.validation.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation to ensure the uniqueness of an email address within the system.
 *
 * <p>This constraint can be applied to any {@code String} field representing an email,
 * and it triggers a database lookup to verify that the email does not already exist.</p>
 *
 * <p>It is commonly used in {@code RequestDTO} classes such as
 * {@code AuthorRequestDTO} or {@code CustomerRequestDTO} to prevent duplicate registrations.</p>
 *
 * <p>It is validated via the {@link com.mavrommatis.ebookshop.ebookshop.validator.validation.UniqueEmailValidator} class,
 * which interacts with the {@code AuthorRepository} to perform the uniqueness check.</p>
 *
 * <p><strong>Note:</strong> This annotation should be used in conjunction with {@code @NotBlank} and {@code @Email}
 * to fully validate the presence and format of the email string.</p>
 *
 * <pre>{@code
 * @NotBlank
 * @Email
 * @UniqueEmail
 * private String email;
 * }</pre>
 *
 * @see com.mavrommatis.ebookshop.ebookshop.validator.validation.UniqueEmailValidator
 */
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    /**
     * Message that will be shown when the validation fails.
     * Default: "Email is already in use"
     */
    String message() default "Email is already in use";
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
