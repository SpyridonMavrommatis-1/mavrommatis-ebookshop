package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     REST controller responsible for managing books
 *     using {@link BookRequestDTO} and {@link BookResponseDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity BookEntity},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO
 * @see com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.basic.BookService
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    /**
     * Constructor-based injection of BookService.
     *
     * @param bookService service handling book operations
     */
    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieve all books.
     * <p>
     *      Accessible to {@code CUSTOMER},
     *      {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @return list of {@link BookResponseDTO}
     *
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<BookResponseDTO> findAll() {
        return bookService.findAll();
    }

    /**
     * Retrieve a single book by its ID.
     *<p>
     *     Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @param bookId the book identifier
     * @return the {@link BookResponseDTO} of the found book
     *
     */
    @GetMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Integer bookId) {
        BookResponseDTO dto = bookService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new book.
     * <p>
     *     Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param request the {@link BookRequestDTO} with book data
     * @return the created {@link BookResponseDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO request) {
        BookResponseDTO created = bookService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing book.
     * <p>
     *     Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param bookId the book ID to update
     * @param request the {@link BookRequestDTO} with updated data
     * @return the updated {@link BookResponseDTO}
     */
    @PutMapping("/{bookId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public BookResponseDTO updateBook(
            @PathVariable Integer bookId,
            @RequestBody BookRequestDTO request
    ) {
        return bookService.update(bookId, request);
    }

    /**
     * Deletes a book by its ID.
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
     * <p>
     *     The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *     instead of returning a {@code ResponseEntity<Void>} with status code 204.
     *     This keeps the controller method signature minimal and declarative.
     * </p>
     * @param bookId the unique identifier of the book to delete
     */
    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    /**
     * Save multiple books in batch.
     * <p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param requests list of {@link BookRequestDTO} entries to save
     * @return list of created {@link BookResponseDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<BookResponseDTO> saveAll(@RequestBody List<BookRequestDTO> requests) {
        return requests.stream()
                .map(bookService::save)
                .toList();
    }

    /**
     * Deletes multiple books by their IDs in a single batch operation.
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
     *
     * @param ids list of book identifiers to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAll(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
    }
}