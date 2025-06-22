package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.validator.annotation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator implementation for the {@link UniqueEmail} annotation.
 *
 * <p>This class checks whether a given email address is unique
 * by querying the {@link AuthorRepository}.</p>
 *
 * <p>If the email already exists in the database (case-insensitive),
 * the validator returns {@code false}.</p>
 *
 * <p>Null or blank values are considered valid here, assuming that
 * annotations like {@code @NotBlank} handle presence validation externally.</p>
 *
 * @see UniqueEmail
 */
@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final AuthorRepository authorRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || email.trim().isEmpty()) {
            return true; // @NotBlank
        }
        return !authorRepository.existsByEmailIgnoreCase(email.trim());
    }
}
