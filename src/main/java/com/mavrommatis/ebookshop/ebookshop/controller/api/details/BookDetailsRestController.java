package com.mavrommatis.ebookshop.ebookshop.controller.api.details;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.details.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *     REST controller responsible for managing books details
 *     using {@link BookDetailsDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity BookDetails},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.details.BookDetailsService
 */
@RestController
@RequestMapping("/api/book-details")
public class BookDetailsRestController {

    private final BookDetailsService bookDetailsService;

    /**
     * Constructor injection for BookDetailsService.
     *
     * @param bookDetailsService service handling BookDetails operations
     */
    @Autowired
    public BookDetailsRestController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Retrieve all book details.
     * <p>
     *     Accessible to {@code CUSTOMER},
     *     {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @return list of {@link BookDetailsDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<BookDetailsDTO> findAll() {
        return bookDetailsService.findAll();
    }

    /**
     * Retrieve a specific book details by book ID.
     *
     * <p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param bookId the book ID
     * @return the {@link BookDetailsDTO}
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookDetailsDTO> findById(@PathVariable Integer bookId) {
        BookDetailsDTO dto = bookDetailsService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create new book details record.
     * <p>
     *   Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param dto the {@link BookDetailsDTO} to create
     * @return the created {@link BookDetailsDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookDetailsDTO> create(@RequestBody BookDetailsDTO dto) {
        BookDetailsDTO created = bookDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update existing book details by book ID.
     *
     * <p>
     *   Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param bookId  the book ID to update details for
     * @param dto the {@link BookDetailsDTO} with updated data
     * @return the updated {@link BookDetailsDTO}
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public BookDetailsDTO update(
            @PathVariable Integer bookId,
            @RequestBody BookDetailsDTO dto
    ) {
        return bookDetailsService.save(dto);
    }

    /**
     * Delete book details by book ID.
     * <p>
     *     Accessible only to users with the {@code ADMIN} role.
     * </p>
     *
     * <p>
     *     This method returns an HTTP 204 (No Content) status code on success, indicating that:
     * </p>
     *
     * <ul>
     *     <li>the request was successfully processed,</li>
     *     <li>but no response body is returned to the client.</li>
     * </ul>
     *
     * <p>
     *   The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *   instead of returning a {@code ResponseEntity<Void>} with status code 204
     *   This keeps the controller method signature minimal and declarative.
     * </p>
     *
     * @param bookId the book ID whose details to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer bookId) {
        bookDetailsService.deleteById(bookId);
    }

    /**
     * Create or update multiple book details in batch.
     *
     * <p>
     *   Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param dtos list of {@link BookDetailsDTO} to process
     * @return list of processed {@link BookDetailsDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<BookDetailsDTO> batchSave(@RequestBody List<BookDetailsDTO> dtos) {
        return bookDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple book details by their book IDs.
     * <p>
     * This endpoint is accessible only to users with the {@code ADMIN} role.
     * <p>
     * This method returns an HTTP 204 (No Content) status code on success, indicating that:
     *   <ul>
     *       <li>the request was successfully processed,</li>
     *       <li>but no response body is returned to the client.</li>
     *   </ul>
     * <p>
     *     The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *     instead of returning a {@code ResponseEntity<Void>} with status code 204.
     *     This keeps the controller method signature minimal and declarative.
     * </p>
     * @param ids list of book IDs whose details to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
    }
}
