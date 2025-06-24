package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidReviewContent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link ValidReviewContent} annotation.
 *
 * <p>Ensures that if a review rating is less than 3, a comment must be provided.</p>
 * <p>Otherwise, the review is considered valid with or without a comment.</p>
 *
 * <p>Null DTOs are treated as valid to avoid failing on framework-level issues.</p>
 *
 * @see ValidReviewContent
 */
public class ValidReviewContentValidator implements ConstraintValidator<ValidReviewContent, BookReviewsRequestDTO> {

    /**
     * Validates that a non-blank comment is provided if the rating is below 3.
     *
     * @param dto     the {@link BookReviewsRequestDTO} to validate
     * @param context the validation context
     * @return {@code true} if valid; otherwise {@code false}
     */
    @Override
    public boolean isValid(BookReviewsRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true; // Safe default for framework-level nulls

        if (dto.getRating() < 3) {
            String comment = dto.getComment();
            return comment != null && !comment.trim().isEmpty();
        }

        return true;
    }
}
