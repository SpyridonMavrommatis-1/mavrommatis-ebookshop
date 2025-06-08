package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link BookDetails} entities.
 * Provides endpoints for CRUD and batch operations, returning JSON responses.
 */
@RestController
@RequestMapping("/api/book-details")
public class BookDetailsRestController {

    private final BookDetailsService bookDetailsService;

    /**
     * Constructor injection for BookDetailsService.
     *
     * @param bookDetailsService the service layer for BookDetails operations
     */
    @Autowired
    public BookDetailsRestController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Retrieves all BookDetails entries.
     *
     * @return a list of BookDetails
     */
    @GetMapping
    public List<BookDetails> findAll() {
        return bookDetailsService.findAll();
    }

    /**
     * Retrieves a specific BookDetails by its ID.
     *
     * @param id the ID of the BookDetails
     * @return the BookDetails object
     * @throws RuntimeException if the entry is not found
     */
    @GetMapping("/{id}")
    public BookDetails findById(@PathVariable int id) {
        return bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
    }

    /**
     * Creates a new BookDetails entry.
     *
     * @param bookDetails the BookDetails object to create
     * @return the saved BookDetails
     */
    @PostMapping
    public BookDetails createBookDetails(@RequestBody BookDetails bookDetails) {
        return bookDetailsService.save(bookDetails);
    }

    /**
     * Updates an existing BookDetails entry.
     *
     * @param id the ID of the BookDetails to update
     * @param bookDetails the updated BookDetails object
     * @return the saved BookDetails
     */
    @PutMapping("/{id}")
    public BookDetails updateBookDetails(@PathVariable int id, @RequestBody BookDetails bookDetails) {
        bookDetails.setBookId(id);
        return bookDetailsService.save(bookDetails);
    }

    /**
     * Saves multiple BookDetails entries in batch.
     *
     * @param bookDetailsList list of BookDetails to save
     * @return the list of saved BookDetails
     */
    @PostMapping("/batch-save-all")
    public List<BookDetails> saveAllBookDetails(@RequestBody List<BookDetails> bookDetailsList) {
        return bookDetailsService.saveAll(bookDetailsList);
    }

    /**
     * Deletes a specific BookDetails entry by ID.
     *
     * @param id the ID of the BookDetails to delete
     * @return a confirmation message
     */
    @DeleteMapping("/delete/{id}")
    public String deleteBookDetails(@PathVariable int id) {
        bookDetailsService.deleteById(id);
        return "BookDetails with id " + id + " deleted.";
    }

    /**
     * Deletes multiple BookDetails entries by their IDs.
     *
     * @param ids list of IDs of the BookDetails to delete
     * @return a confirmation message
     */
    @DeleteMapping("/batch-delete-all")
    public String deleteAllBookDetails(@RequestBody List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
        return "BookDetails with ids " + ids + " deleted.";
    }
}
