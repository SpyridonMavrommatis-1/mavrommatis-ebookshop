package com.mavrommatis.ebookshop.ebookshop.validator.validation;

import com.mavrommatis.ebookshop.ebookshop.validator.annotation.ValidURL;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Validator implementation for the {@link ValidURL} annotation.
 *
 * <p>This class uses {@link java.net.URL} to check if a given string is:</p>
 * <ul>
 *     <li>well-formed as a URL</li>
 *     <li>uses "http" or "https" as protocol</li>
 *     <li>contains a domain part (e.g., example.com)</li>
 * </ul>
 *
 * <p>Null or blank values are considered valid, assuming other annotations like {@code @NotBlank}
 * handle presence requirements.</p>
 *
 * @see ValidURL
 */
public class ValidURLValidator implements ConstraintValidator<ValidURL, String> {

    /**
     * Validates that the input string is a syntactically valid URL with an accepted protocol.
     *
     * @param value   the string to validate
     * @param context the validation context
     * @return {@code true} if the input is null/blank or a valid URL; otherwise {@code false}
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().isEmpty()) return true;
        try {
            URL url = new URL(value);
            return url.getProtocol().matches("https?") && url.getHost().contains(".");
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
