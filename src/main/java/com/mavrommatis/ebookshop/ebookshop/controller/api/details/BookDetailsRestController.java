package com.mavrommatis.ebookshop.ebookshop.controller.api.details;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.details.BookDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller responsible for managing books details using {@link BookDetailsDTO}.
 * Provides CRUD and batch operations with validation and role-based access.
 */
@RestController
@RequestMapping("/api/book-details")
public class BookDetailsRestController {

    private final BookDetailsService bookDetailsService;

    @Autowired
    public BookDetailsRestController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Retrieve all book details.
     * Accessible to CUSTOMER, EMPLOYEE and ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<BookDetailsDTO> findAll() {
        return bookDetailsService.findAll();
    }

    /**
     * Retrieve a specific book details by book ID.
     * Accessible to CUSTOMER, EMPLOYEE and ADMIN.
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookDetailsDTO> findById(@PathVariable Integer bookId) {
        BookDetailsDTO dto = bookDetailsService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create new book details record.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookDetailsDTO> create(@Valid @RequestBody BookDetailsDTO dto) {
        BookDetailsDTO created = bookDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update existing book details by book ID.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public BookDetailsDTO update(
            @PathVariable Integer bookId,
            @Valid @RequestBody BookDetailsDTO dto
    ) {
        return bookDetailsService.save(dto);
    }

    /**
     * Delete book details by book ID.
     * Accessible only to ADMIN.
     */
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer bookId) {
        bookDetailsService.deleteById(bookId);
    }

    /**
     * Create or update multiple book details in batch.
     * Accessible to EMPLOYEE and ADMIN.
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<BookDetailsDTO> batchSave(@Valid @RequestBody List<@Valid BookDetailsDTO> dtos) {
        return bookDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple book details by their book IDs.
     * Accessible only to ADMIN.
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
    }
}
