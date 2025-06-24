package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidISBN;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link ValidISBN} annotation.
 *
 * <p>This validator checks whether a given string represents a valid ISBN-10 or ISBN-13
 * based on format and checksum logic.</p>
 *
 * <ul>
 *   <li>Strips hyphens and whitespace before processing</li>
 *   <li>Supports both ISBN-10 and ISBN-13 formats</li>
 *   <li>Uses Modulo 11 checksum for ISBN-10 and Modulo 10 for ISBN-13</li>
 *   <li>Returns true if the field is null or blank, assuming presence validation is handled elsewhere</li>
 * </ul>
 *
 * @see ValidISBN
 */
public class ValidISBNValidator implements ConstraintValidator<ValidISBN, String> {

    /**
     * Validates whether the provided string is a valid ISBN.
     *
     * @param value   the input string to validate
     * @param context the validation context
     * @return {@code true} if the value is null, blank, or a valid ISBN-10/13; otherwise {@code false}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        String isbn = value.replaceAll("[-\\s]", "");

        if (isbn.length() == 10) return isValidIsbn10(isbn);
        if (isbn.length() == 13) return isValidIsbn13(isbn);

        return false;
    }

    /**
     * Validates ISBN-10 format using Modulo 11 checksum.
     *
     * @param isbn cleaned 10-character string without hyphens or spaces
     * @return {@code true} if ISBN-10 is valid; otherwise {@code false}
     */
    private boolean isValidIsbn10(String isbn) {
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
            sum += (isbn.charAt(i) - '0') * (10 - i);
        }
        char checksum = isbn.charAt(9);
        sum += (checksum == 'X') ? 10 : (Character.isDigit(checksum) ? checksum - '0' : -1);
        return sum % 11 == 0;
    }

    /**
     * Validates ISBN-13 format using Modulo 10 checksum.
     *
     * @param isbn cleaned 13-character string without hyphens or spaces
     * @return {@code true} if ISBN-13 is valid; otherwise {@code false}
     */
    private boolean isValidIsbn13(String isbn) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            if (!Character.isDigit(isbn.charAt(i))) return false;
            int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }
}
