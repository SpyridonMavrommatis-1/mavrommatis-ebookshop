package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that handles HTTP requests for managing {@link Book} entities.
 * Supports full CRUD operations and ensures bidirectional relationships with {@link Author} and {@link BookDetails}.
 */
@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;
    private final AuthorService authorService;

    /**
     * Constructor with dependency injection for {@link BookService} and {@link AuthorService}.
     *
     * @param bookService   the service used to manage book entities
     * @param authorService the service used to manage author entities
     */
    @Autowired
    public BookRestController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    /**
     * Retrieves a list of all books from the database.
     *
     * @return a list of all {@link Book} entities
     */
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    /**
     * Retrieves a single book by its unique ID.
     *
     * @param id the ID of the book to retrieve
     * @return the {@link Book} entity, if found
     * @throws RuntimeException if the book is not found
     */
    @GetMapping("/{id}")
    public Book findById(@PathVariable int id) {
        return bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    /**
     * Creates a new book, assigning it to an existing author and linking any book details.
     *
     * @param book the {@link Book} entity sent in the request body
     * @return the newly saved {@link Book}
     * @throws RuntimeException if the author is not found or the ID is missing
     */
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book); // maintain bidirectional link
        }

        if (book.getAuthor() != null && book.getAuthor().getAuthorId() != 0) {
            Author managedAuthor = authorService.findById(book.getAuthor().getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + book.getAuthor().getAuthorId()));
            book.setAuthor(managedAuthor);
            managedAuthor.addBook(book); // maintain bidirectional link
        } else {
            throw new RuntimeException("Author ID is required to create a book.");
        }

        return bookService.save(book);
    }

    /**
     * Updates an existing {@link Book} entity based on the provided ID and request body.
     * <p>
     * This method delegates the update logic to the {@link com.mavrommatis.ebookshop.ebookshop.service.BookService},
     * which ensures that all necessary relationships (e.g., {@link com.mavrommatis.ebookshop.ebookshop.entity.Author},
     * {@link com.mavrommatis.ebookshop.ebookshop.entity.BookDetails}) are handled appropriately.
     *
     * @param id   the ID of the book to be updated
     * @param book the updated {@link Book} object received in the request body
     * @return the updated {@link Book} entity after persistence
     * @throws RuntimeException if the book or author does not exist
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setBookId(id);
        return bookService.update(book);
    }

    /**
     * Deletes a book by its unique ID.
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
     * Saves multiple books in batch, validating authors and book details.
     *
     * @param books the list of {@link Book} entities to save
     * @return the list of saved {@link Book} entities
     * @throws RuntimeException if any book has missing or invalid author ID
     */
    @PostMapping("/batch-save-all")
    public List<Book> saveAllBooks(@RequestBody List<Book> books) {
        for (Book book : books) {
            BookDetails details = book.getBookDetails();
            if (details != null) {
                details.setBook(book);
            }

            if (book.getAuthor() != null && book.getAuthor().getAuthorId() != 0) {
                Author managedAuthor = authorService.findById(book.getAuthor().getAuthorId())
                        .orElseThrow(() -> new RuntimeException("Author not found with id: " + book.getAuthor().getAuthorId()));
                book.setAuthor(managedAuthor);
                managedAuthor.addBook(book);
            } else {
                throw new RuntimeException("Author ID is required for book: " + book.getTitle());
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
    @DeleteMapping("/batch-delete-all")
    public String deleteAllBooks(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
        return "Books with ids " + ids + " deleted.";
    }
}