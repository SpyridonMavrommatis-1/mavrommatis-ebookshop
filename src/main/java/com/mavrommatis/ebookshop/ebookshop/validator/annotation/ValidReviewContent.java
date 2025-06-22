package com.mavrommatis.ebookshop.ebookshop.validator.annotation;

import com.mavrommatis.ebookshop.ebookshop.validator.validation.ValidReviewContentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation for book review content.
 *
 * <p>Ensures that if a review rating is 1 or 2 (i.e., low),
 * a meaningful comment must be provided.</p>
 */
@Documented
@Constraint(validatedBy = ValidReviewContentValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReviewContent {

    /**
     * Default validation failure message.
     */
    String message() default "Comment is required for ratings below 3";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

