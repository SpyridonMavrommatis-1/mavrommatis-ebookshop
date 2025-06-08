package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web controller for managing {@link Book} entities via Thymeleaf views.
 * Mirrors the structure of the REST controller, adapted for HTML pages.
 */
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructor-based dependency injection for BookService.
     *
     * @param bookService the service layer for book-related operations
     */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books and shows them in a list view.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping
    public String findAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    /**
     * Retrieves a book by ID and shows the detail view.
     *
     * @param id the ID of the book
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        model.addAttribute("book", book);
        return "book-by-id";
    }

    /**
     * Shows the form to create a new book.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/find/create-new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    /**
     * Creates a new book from form data.
     *
     * @param book the book object
     * @return redirection to the book list
     */
    @PostMapping
    public String createBook(@ModelAttribute("book") Book book) {
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book); // Maintain the bidirectional relationship
        }
        bookService.save(book);
        return "redirect:/books";
    }

    /**
     * Shows the form to update an existing book.
     *
     * @param id the ID of the book
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/get-edit/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        model.addAttribute("book", book);
        return "book-form";
    }

    /**
     * Updates an existing book from form data.
     *
     * @param id the ID of the book
     * @param book the updated book object
     * @return redirection to the book list
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") Book book) {
        book.setBookId(id);
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book);
        }
        bookService.save(book);
        return "redirect:/books";
    }

    /**
     * Shows a form to add multiple books.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/batch-create-many")
    public String showBatchSaveForm(Model model) {
        model.addAttribute("bookList", List.of(new Book(), new Book())); // Example with 2 empty rows
        return "book-batch-form";
    }

    /**
     * Saves multiple books from form data.
     *
     * @param books the list of books
     * @return redirection to the book list
     */
    @PostMapping("/batch-save-all")
    public String saveAllBooks(@ModelAttribute("bookList") List<Book> books) {
        for (Book book : books) {
            BookDetails details = book.getBookDetails();
            if (details != null) {
                details.setBook(book);
            }
        }
        bookService.saveAll(books);
        return "redirect:/books";
    }

    /**
     * Deletes a book by ID.
     *
     * @param id the ID of the book
     * @return redirection to the book list
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    /**
     * Shows a form to select multiple books for deletion.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/batch-show-all")
    public String showBatchDeleteForm(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-batch-show-delete-all";
    }

    /**
     * Deletes multiple books by their IDs.
     *
     * @param ids the list of IDs
     * @return redirection to the book list
     */
    @PostMapping("/batch-delete")
    public String deleteAllBooks(@RequestParam List<Integer> ids) {
        bookService.deleteAllById(ids);
        return "redirect:/books";
    }
}