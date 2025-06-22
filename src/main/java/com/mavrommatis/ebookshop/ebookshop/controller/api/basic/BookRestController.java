package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing books
 * via DTOs, enforcing validation and role-based access.
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieve all books.
     * Accessible to CUSTOMER, EMPLOYEE and ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<BookResponseDTO> findAll() {
        return bookService.findAll();
    }

    /**
     * Retrieve a book by ID.
     * Accessible to CUSTOMER, EMPLOYEE and ADMIN.
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Integer bookId) {
        BookResponseDTO dto = bookService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new book.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO request) {
        BookResponseDTO created = bookService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing book.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public BookResponseDTO updateBook(
            @PathVariable Integer bookId,
            @Valid @RequestBody BookRequestDTO request
    ) {
        return bookService.update(bookId, request);
    }

    /**
     * Delete a book by ID.
     * Accessible only to ADMIN.
     */
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    /**
     * Batch create books.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<BookResponseDTO> saveAll(@Valid @RequestBody List<@Valid BookRequestDTO> requests) {
        return requests.stream()
                .map(bookService::save)
                .toList();
    }

    /**
     * Batch delete books by IDs.
     * Accessible only to ADMIN.
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
    }
}
