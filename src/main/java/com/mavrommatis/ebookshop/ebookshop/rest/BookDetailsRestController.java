package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing book details via DTOs.
 * Exposes endpoints for CRUD operations and batch processing.
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
     *
     * @return list of {@link BookDetailsDTO}
     */
    @GetMapping
    public List<BookDetailsDTO> findAll() {
        return bookDetailsService.findAll();
    }

    /**
     * Retrieve a specific book details by book ID.
     *
     * @param bookId the book ID
     * @return the {@link BookDetailsDTO}
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailsDTO> findById(@PathVariable Integer bookId) {
        BookDetailsDTO dto = bookDetailsService.findById(bookId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create new book details record.
     *
     * @param dto the {@link BookDetailsDTO} to create
     * @return the created {@link BookDetailsDTO}
     */
    @PostMapping
    public ResponseEntity<BookDetailsDTO> create(@RequestBody BookDetailsDTO dto) {
        BookDetailsDTO created = bookDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update existing book details by book ID.
     *
     * @param bookId  the book ID to update details for
     * @param dto the {@link BookDetailsDTO} with updated data
     * @return the updated {@link BookDetailsDTO}
     */
    @PutMapping("/{bookId}")
    public BookDetailsDTO update(
            @PathVariable Integer bookId,
            @RequestBody BookDetailsDTO dto
    ) {
        return bookDetailsService.save(dto);
    }

    /**
     * Delete book details by book ID.
     *
     * @param bookId the book ID whose details to delete
     * @return 204 No Content
     */
    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer bookId) {
        bookDetailsService.deleteById(bookId);
    }

    /**
     * Create or update multiple book details in batch.
     *
     * @param dtos list of {@link BookDetailsDTO} to process
     * @return list of processed {@link BookDetailsDTO}
     */
    @PostMapping("/batch")
    public List<BookDetailsDTO> batchSave(@RequestBody List<BookDetailsDTO> dtos) {
        return bookDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple book details by their book IDs.
     *
     * @param ids list of book IDs whose details to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@RequestBody List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
    }
}
