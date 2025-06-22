package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidReviewContent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator for {@link ValidReviewContent} annotation.
 *
 * <p>Enforces that if the rating is less than 3, then a non-blank comment must be provided.</p>
 */
public class ValidReviewContentValidator implements ConstraintValidator<ValidReviewContent, BookReviewsRequestDTO> {

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
