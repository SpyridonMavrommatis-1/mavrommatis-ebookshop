package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link AuthorEntity} entities.
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
    public List<AuthorEntity> findAll() {
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
    public AuthorEntity findById(@PathVariable int id) {
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
    public AuthorEntity createAuthor(@RequestBody AuthorEntity author) {
        AuthorDetailsEntity details = author.getAuthorDetails();
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
    public AuthorEntity updateAuthor(@PathVariable int id, @RequestBody AuthorEntity author) {
        author.setAuthorId(id);
        AuthorDetailsEntity details = author.getAuthorDetails();
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
    public List<AuthorEntity> saveAllAuthors(@RequestBody List<AuthorEntity> authors) {
        for (AuthorEntity author : authors) {
            AuthorDetailsEntity details = author.getAuthorDetails();
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
