package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link AuthorDetails} entities.
 * Provides JSON-based endpoints for CRUD operations.
 */
@RestController
@RequestMapping("/api/authordetails")
public class AuthorDetailsRestController {

    private final AuthorDetailsService authorDetailsService;

    /**
     * Constructor-based dependency injection for AuthorDetailsService.
     *
     * @param authorDetailsService the service layer for author details
     */
    @Autowired
    public AuthorDetailsRestController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    /**
     * Retrieves all author details.
     *
     * @return a list of AuthorDetails
     */
    @GetMapping
    public List<AuthorDetails> findAll() {
        return authorDetailsService.findAll();
    }

    /**
     * Retrieves author details by ID.
     *
     * @param id the ID of the author details
     * @return the AuthorDetails object
     * @throws RuntimeException if not found
     */
    @GetMapping("/{id}")
    public AuthorDetails findById(@PathVariable int id) {
        return authorDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found with id: " + id));
    }

    /**
     * Creates a new AuthorDetails entry.
     *
     * @param authorDetails the details to create
     * @return the created AuthorDetails
     */
    @PostMapping
    public AuthorDetails createAuthorDetails(@RequestBody AuthorDetails authorDetails) {
        return authorDetailsService.save(authorDetails);
    }

    /**
     * Updates an existing AuthorDetails entry by ID.
     *
     * @param id the ID to update
     * @param authorDetails the updated details
     * @return the updated AuthorDetails
     */
    @PutMapping("/{id}")
    public AuthorDetails updateAuthorDetails(@PathVariable int id, @RequestBody AuthorDetails authorDetails) {
        authorDetails.setAuthorId(id);
        return authorDetailsService.save(authorDetails);
    }

    /**
     * Saves multiple AuthorDetails entries.
     *
     * @param authorDetailsList the list of details to save
     * @return the list of saved AuthorDetails
     */
    @PostMapping("/batch")
    public List<AuthorDetails> saveAllAuthorDetails(@RequestBody List<AuthorDetails> authorDetailsList) {
        return authorDetailsService.saveAll(authorDetailsList);
    }

    /**
     * Deletes author details by ID.
     *
     * @param id the ID to delete
     * @return confirmation message
     */
    @DeleteMapping("/{id}")
    public String deleteAuthorDetails(@PathVariable int id) {
        authorDetailsService.deleteById(id);
        return "AuthorDetails with id " + id + " deleted.";
    }

    /**
     * Deletes multiple AuthorDetails entries by their IDs.
     *
     * @param ids the list of IDs to delete
     * @return confirmation message
     */
    @DeleteMapping("/batch")
    public String deleteAllAuthorDetails(@RequestBody List<Integer> ids) {
        authorDetailsService.deleteAllById(ids);
        return "AuthorDetails with ids " + ids + " deleted.";
    }
}
