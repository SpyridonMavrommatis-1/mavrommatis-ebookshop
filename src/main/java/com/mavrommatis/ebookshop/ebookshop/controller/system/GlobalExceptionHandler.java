package com.mavrommatis.ebookshop.ebookshop.controller.system;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Global exception handler that intercepts and formats exceptions thrown by the application.
 * <p>
 * This class provides centralized error handling using Spring's {@link RestControllerAdvice},
 * returning consistent JSON responses with appropriate HTTP status codes.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all not-found exceptions across the application.
     *
     * @param ex      the exception thrown
     * @param request the originating HTTP request
     * @return formatted response entity with 404 status
     */
    @ExceptionHandler({
            com.mavrommatis.ebookshop.ebookshop.exception.book.BookNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.book.AuthorNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.book.BookDetailsNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.author.AuthorNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.author.AuthorDetailsNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerDetailsNotFoundException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.review.ReviewNotFoundException.class
    })
    public ResponseEntity<Map<String, Object>> handleNotFound(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Handles authorization errors such as forbidden actions.
     *
     * @param ex      the exception thrown
     * @param request the originating HTTP request
     * @return formatted response entity with 403 status
     */
    @ExceptionHandler({
            com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerAccessDeniedException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.review.UnauthorizedReviewActionException.class
    })
    public ResponseEntity<Map<String, Object>> handleForbidden(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Handles conflicts, such as duplicate submissions.
     *
     * @param ex      the exception thrown
     * @param request the originating HTTP request
     * @return formatted response entity with 409 status
     */
    @ExceptionHandler({
            com.mavrommatis.ebookshop.ebookshop.exception.review.DuplicateReviewException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    /**
     * Handles validation errors triggered by invalid DTOs.
     *
     * @param ex      the exception thrown
     * @param request the originating HTTP request
     * @return formatted response entity with 400 status and detailed field errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex,
                                                                      HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return buildResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    /**
     * Handles unexpected internal errors.
     *
     * @param ex      any uncaught exception
     * @param request the originating HTTP request
     * @return formatted response entity with 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: " + ex.getMessage(), request.getRequestURI());
    }

    /**
     * Helper method to construct a consistent response body.
     *
     * @param status  HTTP status code
     * @param message exception or error message
     * @param path    the endpoint path that triggered the exception
     * @return formatted {@link ResponseEntity} containing a map of error details
     */
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", ZonedDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }
}
