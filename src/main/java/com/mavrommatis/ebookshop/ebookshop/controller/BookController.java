package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing books using Thymeleaf views.
 */
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    /**
     * Constructor-based dependency injection for BookService.
     *
     * @param bookService the service layer for book operations
     */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Displays a list of all books.
     *
     * @param model the Spring model to pass data to the view
     * @return the view name for the book list
     */
    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-list";
    }

    /**
     * Displays the details of a specific book.
     *
     * @param id the ID of the book
     * @param model the Spring model to pass data to the view
     * @return the view name for the book details
     */
    @GetMapping("/{id}")
    public String showBook(@PathVariable int id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        model.addAttribute("book", book);
        return "book-details";
    }

    /**
     * Displays the form to create a new book.
     *
     * @param model the Spring model to pass data to the view
     * @return the view name for the book form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    /**
     * Handles form submission for creating a new book.
     *
     * @param book the book object populated from the form
     * @return redirection to the book list page
     */
    @PostMapping
    public String saveBook(@ModelAttribute("book") Book book) {
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }
        bookService.save(book);
        return "redirect:/books";
    }

    /**
     * Displays the form to edit an existing book.
     *
     * @param id the ID of the book
     * @param model the Spring model to pass data to the view
     * @return the view name for the book form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        model.addAttribute("book", book);
        return "book-form";
    }

    /**
     * Handles form submission for updating an existing book.
     *
     * @param id the ID of the book to update
     * @param book the updated book object
     * @return redirection to the book list page
     */
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable int id, @ModelAttribute("book") Book book) {
        book.setBookId(id);
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }
        bookService.save(book);
        return "redirect:/books";
    }

    /**
     * Deletes a specific book by ID.
     *
     * @param id the ID of the book to delete
     * @return redirection to the book list page
     */
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    /**
     * Displays the form to delete multiple books.
     *
     * @param model the Spring model to pass data to the view
     * @return the view name for batch deletion
     */
    @GetMapping("/batch-delete")
    public String showBatchDeleteForm(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "book-batch-delete";
    }

    /**
     * Handles batch deletion of selected books.
     *
     * @param ids list of book IDs to delete
     * @return redirection to the book list page
     */
    @PostMapping("/batch-delete")
    public String deleteSelectedBooks(@RequestParam List<Integer> ids) {
        bookService.deleteAllById(ids);
        return "redirect:/books";
    }
}
