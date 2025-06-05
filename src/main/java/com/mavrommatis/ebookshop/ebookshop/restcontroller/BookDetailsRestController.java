package com.mavrommatis.ebookshop.ebookshop.restcontroller;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookdetails")
public class BookDetailsRestController {

    private final BookDetailsService bookDetailsService;

    @Autowired
    public BookDetailsRestController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    // GET /api/bookdetails
    @GetMapping
    public List<BookDetails> findAll() {
        return bookDetailsService.findAll();
    }

    // GET /api/bookdetails/{id}
    @GetMapping("/{id}")
    public BookDetails findById(@PathVariable int id) {
        return bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
    }

    // POST /api/bookdetails
    @PostMapping
    public BookDetails createBookDetails(@RequestBody BookDetails bookDetails) {
        return bookDetailsService.save(bookDetails);
    }

    // PUT /api/bookdetails/{id}
    @PutMapping("/{id}")
    public BookDetails updateBookDetails(@PathVariable int id, @RequestBody BookDetails bookDetails) {
        bookDetails.setBookId(id);
        return bookDetailsService.save(bookDetails);
    }

    // POST /api/bookdetails/batch
    @PostMapping("/batch")
    public List<BookDetails> saveAllBookDetails(@RequestBody List<BookDetails> bookDetailsList) {
        return bookDetailsService.saveAll(bookDetailsList);
    }

    // DELETE /api/bookdetails/{id}
    @DeleteMapping("/{id}")
    public String deleteBookDetails(@PathVariable int id) {
        bookDetailsService.deleteById(id);
        return "BookDetails with id " + id + " deleted.";
    }

    // DELETE /api/bookdetails/batch
    @DeleteMapping("/batch")
    public String deleteAllBookDetails(@RequestBody List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
        return "BookDetails with ids " + ids + " deleted.";
    }
}