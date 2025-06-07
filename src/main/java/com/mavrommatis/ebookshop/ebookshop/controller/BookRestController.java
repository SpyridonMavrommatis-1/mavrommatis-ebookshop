package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that provides CRUD operations for {@link Book} entities.
 * Responds with JSON and is typically used for API clients (e.g., JavaScript frontends).
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    /**
     * Constructs the controller with an injected {@link BookService}.
     *
     * @param bookService the service layer for book operations
     */
    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books.
     *
     * @return a list of all books
     */
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return the book with the given ID
     * @throws RuntimeException if the book is not found
     */
    @GetMapping("/{id}")
    public Book findById(@PathVariable int id) {
        return bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    /**
     * Creates a new book.
     *
     * @param book the book object sent in the request body
     * @return the saved book
     */
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book); // maintain bidirectional relationship
        }
        return bookService.save(book);
    }

    /**
     * Updates an existing book with the given ID.
     *
     * @param id the ID of the book to update
     * @param book the updated book object
     * @return the updated book
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setBookId(id);
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book);
        }
        return bookService.save(book);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return "Book with id " + id + " deleted.";
    }

    /**
     * Saves a list of books in batch.
     *
     * @param books the list of books to save
     * @return the list of saved books
     */
    @PostMapping("/batch")
    public List<Book> saveAllBooks(@RequestBody List<Book> books) {
        for (Book book : books) {
            BookDetails details = book.getBookDetails();
            if (details != null) {
                details.setBook(book);
            }
        }
        return bookService.saveAll(books);
    }

    /**
     * Deletes multiple books by their IDs.
     *
     * @param ids the list of book IDs to delete
     * @return a confirmation message
     */
    @DeleteMapping("/batch")
    public String deleteAllBooks(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
        return "Books with ids " + ids + " deleted.";
    }
}