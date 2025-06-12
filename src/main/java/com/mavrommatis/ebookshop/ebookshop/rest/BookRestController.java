package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing books via DTOs.
 * Provides endpoints for CRUD operations, returning DTOs instead of entities.
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
     *
     * @return list of {@link BookResponseDTO}
     */
    @GetMapping
    public List<BookResponseDTO> findAll() {
        return bookService.findAll();
    }

    /**
     * Retrieve a single book by its ID.
     *
     * @param bookId the book identifier
     * @return the {@link BookResponseDTO} of the found book
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Integer bookId) {
        BookResponseDTO dto = bookService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new book.
     *
     * @param request the {@link BookRequestDTO} with book data
     * @return the created {@link BookResponseDTO}
     */
    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO request) {
        BookResponseDTO created = bookService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing book.
     *
     * @param bookId      the book ID to update
     * @param request the {@link BookRequestDTO} with updated data
     * @return the updated {@link BookResponseDTO}
     */
    @PutMapping("/{bookId}")
    public BookResponseDTO updateBook(
            @PathVariable Integer bookId,
            @RequestBody BookRequestDTO request
    ) {
        return bookService.update(bookId, request);
    }

    /**
     * Delete a book by its ID.
     *
     * @param bookId the identifier of the book to delete
     * @return 204 No Content on successful deletion
     */
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Integer bookId) {
        bookService.deleteById(bookId);
    }

    /**
     * Save multiple books in batch.
     *
     * @param requests list of {@link BookRequestDTO} entries to save
     * @return list of created {@link BookResponseDTO}
     */
    @PostMapping("/batch")
    public List<BookResponseDTO> saveAll(@RequestBody List<BookRequestDTO> requests) {
        return requests.stream()
                .map(bookService::save)
                .toList();
    }

    /**
     * Delete multiple books by their IDs in batch.
     *
     * @param ids list of book identifiers to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
    }
}