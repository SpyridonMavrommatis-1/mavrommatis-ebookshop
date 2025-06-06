package com.mavrommatis.ebookshop.ebookshop.restcontroller;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // GET /api/authors
    @GetMapping
    public List<Author> findAll() {
        return authorService.findAll();
    }

    // GET /api/authors/{id}
    @GetMapping("/{id}")
    public Author findById(@PathVariable int id) {
        return authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    // POST /api/authors
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author);  // Maintain the bidirectional relationship
        }
        return authorService.save(author);
    }

    // PUT /api/authors/{id}
    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable int id, @RequestBody Author author) {
        author.setAuthorId(id);
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author);
        }
        return authorService.save(author);
    }

    // DELETE /api/authors/{id}
    @DeleteMapping("/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteById(id);
        return "Author with id " + id + " deleted.";
    }

    // POST /api/authors/batch
    @PostMapping("/batch")
    public List<Author> saveAllAuthors(@RequestBody List<Author> authors) {
        for (Author author : authors) {
            AuthorDetails details = author.getAuthorDetails();
            if (details != null) {
                details.setAuthor(author);
            }
        }
        return authorService.saveAll(authors);
    }

    // DELETE /api/authors/batch
    @DeleteMapping("/batch")
    public String deleteAllAuthors(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
        return "Authors with ids " + ids + " deleted.";
    }
}
