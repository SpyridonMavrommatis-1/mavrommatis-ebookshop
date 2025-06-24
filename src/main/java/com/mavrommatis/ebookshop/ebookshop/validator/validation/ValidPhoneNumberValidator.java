package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link ValidPhoneNumber}.
 *
 * <p>This class validates that a string value is a valid phone number.
 * It allows digits, optional "+" or "00" prefix, and enforces a reasonable length.</p>
 *
 * <p>Null or empty values are considered valid, assuming presence is handled elsewhere
 * with {@code @NotBlank} or similar annotations.</p>
 *
 * @see ValidPhoneNumber
 */
public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final String PHONE_REGEX = "^(\\+|00)?\\d{8,20}$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        return value.trim().matches(PHONE_REGEX);
    }
}