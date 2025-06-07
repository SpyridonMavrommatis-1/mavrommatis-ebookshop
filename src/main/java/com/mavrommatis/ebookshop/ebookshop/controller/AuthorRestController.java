package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link Author} entities.
 * Exposes endpoints for CRUD and batch operations over JSON.
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    /**
     * Constructor-based dependency injection for AuthorService.
     *
     * @param authorService the service layer for author-related operations
     */
    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieves all authors.
     *
     * @return a list of all authors
     */
    @GetMapping
    public List<Author> findAll() {
        return authorService.findAll();
    }

    /**
     * Retrieves an author by ID.
     *
     * @param id the ID of the author to retrieve
     * @return the author with the given ID
     * @throws RuntimeException if the author is not found
     */
    @GetMapping("/find/{id}")
    public Author findById(@PathVariable int id) {
        return authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    /**
     * Creates a new author.
     *
     * @param author the author object sent in the request body
     * @return the saved author
     */
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author); // Maintain the bidirectional relationship
        }
        return authorService.save(author);
    }

    /**
     * Updates an existing author with the given ID.
     *
     * @param id the ID of the author to update
     * @param author the updated author object
     * @return the updated author
     */
    @PutMapping("/update/{id}")
    public Author updateAuthor(@PathVariable int id, @RequestBody Author author) {
        author.setAuthorId(id);
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author);
        }
        return authorService.save(author);
    }

    /**
     * Deletes an author by ID.
     *
     * @param id the ID of the author to delete
     * @return a confirmation message
     */
    @DeleteMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteById(id);
        return "Author with id " + id + " deleted.";
    }

    /**
     * Creates multiple authors in batch.
     *
     * @param authors a list of authors to create
     * @return the list of saved authors
     */
    @PostMapping("/batch-save")
    public List<Author> saveAllAuthors(@RequestBody List<Author> authors) {
        for (Author author : authors) {
            AuthorDetails details = author.getAuthorDetails();
            if (details != null) {
                details.setAuthor(author);
            }
        }
        return authorService.saveAll(authors);
    }

    /**
     * Deletes multiple authors by their IDs.
     *
     * @param ids a list of author IDs to delete
     * @return a confirmation message
     */
    @DeleteMapping("/batch-delete")
    public String deleteAllAuthors(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
        return "Authors with ids " + ids + " deleted.";
    }
}
