package com.mavrommatis.ebookshop.ebookshop.restcontroller;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    // Τhis method answers the call of ("/api/books") and gets all books
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    // supplements the GET /api/books/{id}
    @GetMapping("/{id}")
    public Book findById(@PathVariable int id) {
        return bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    // POST /api/books
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book);  // ensure the relationship is maintained because of lazy fetch
        }
        return bookService.save(book);
    }

    // PUT /api/books/{id}
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable int id, @RequestBody Book book) {
        book.setBookId(id);
        BookDetails details = book.getBookDetails();
        if (details != null) {
            details.setBook(book);  // ensure the relationship is maintained because of lazy fetch
        }
        return bookService.save(book);
    }

    // DELETE /api/books/{id}
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteById(id);
        return "Book with id " + id + " deleted.";
    }

    // POST /api/books/batch
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

    // DELETE api/books/batch
    @DeleteMapping("/batch")
    public String deleteAllBooks(@RequestBody List<Integer> ids) {
        bookService.deleteAllById(ids);
        return "Books with ids " + ids + " deleted.";
    }
}
