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

@RestControllerAdvice
public class GlobalExceptionHandler {

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

    @ExceptionHandler({
            com.mavrommatis.ebookshop.ebookshop.exception.customer.CustomerAccessDeniedException.class,
            com.mavrommatis.ebookshop.ebookshop.exception.review.UnauthorizedReviewActionException.class
    })
    public ResponseEntity<Map<String, Object>> handleForbidden(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({
            com.mavrommatis.ebookshop.ebookshop.exception.review.DuplicateReviewException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflict(RuntimeException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unexpected error: " + ex.getMessage(), request.getRequestURI());
    }

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