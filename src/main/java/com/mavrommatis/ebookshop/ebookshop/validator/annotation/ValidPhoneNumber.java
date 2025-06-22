package com.mavrommatis.ebookshop.ebookshop.validator.annotation;

import com.mavrommatis.ebookshop.ebookshop.validator.validation.ValidPhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom annotation for validating phone numbers.
 *
 * <p>This constraint ensures that the annotated string value
 * represents a valid phone number in terms of digit-only structure,
 * optional international prefix, and basic length requirements.</p>
 *
 * <p>Format rules may be adjusted in the corresponding validator.</p>
 *
 * Example accepted formats:
 * <ul>
 *     <li>"2101234567"</li>
 *     <li>"+302101234567"</li>
 *     <li>"00302101234567"</li>
 * </ul>
 */
@Documented
@Constraint(validatedBy = ValidPhoneNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
