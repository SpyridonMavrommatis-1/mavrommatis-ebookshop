package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing author profile details via DTOs.
 * <p>
 * Exposes CRUD and batch operations on {@link AuthorDetailsDTO}.
 * All endpoints produce and consume JSON.
 * </p>
 */
@RestController
@RequestMapping("/api/author-details")
public class AuthorDetailsRestController {

    private final AuthorDetailsService authorDetailsService;

    /**
     * Constructor-based injection of the service layer.
     *
     * @param authorDetailsService the service handling author details business logic
     */
    @Autowired
    public AuthorDetailsRestController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    /**
     * Retrieve all author details records.
     *
     * @return a list of {@link AuthorDetailsDTO} for all authors
     */
    @GetMapping
    public List<AuthorDetailsDTO> findAll() {
        return authorDetailsService.findAll();
    }

    /**
     * Retrieve a specific author details record by its ID.
     *
     * @param authorId the unique identifier of the author
     * @return 200 OK with the {@link AuthorDetailsDTO} if found,
     *         or 404 NOT FOUND if no record exists
     */
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorDetailsDTO> findById(@PathVariable Integer authorId) {
        AuthorDetailsDTO dto = authorDetailsService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new author details record.
     *
     * @param dto the {@link AuthorDetailsDTO} containing new author profile data
     * @return 201 CREATED with the persisted {@link AuthorDetailsDTO}
     */
    @PostMapping
    public ResponseEntity<AuthorDetailsDTO> create(
            @RequestBody AuthorDetailsDTO dto
    ) {
        AuthorDetailsDTO created = authorDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing author details record.
     *
     * @param authorId the ID of the author details to update
     * @param dto      the {@link AuthorDetailsDTO} containing updated profile data
     * @return 200 OK with the updated {@link AuthorDetailsDTO}
     */
    @PutMapping("/{authorId}")
    public AuthorDetailsDTO update(
            @PathVariable Integer authorId,
            @RequestBody AuthorDetailsDTO dto
    ) {
        // The service layer handles upsert semantics
        return authorDetailsService.save(dto);
    }

    /**
     * Delete an author details record by its ID.
     *
     * @param authorId the ID of the author details to delete
     * @return 204 NO CONTENT on successful deletion
     */
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer authorId) {
        authorDetailsService.deleteById(authorId);
    }

    /**
     * Batch create or update multiple author details records.
     *
     * @param dtos a list of {@link AuthorDetailsDTO} to persist
     * @return 200 OK with the list of persisted {@link AuthorDetailsDTO}
     */
    @PostMapping("/batch")
    public List<AuthorDetailsDTO> batchSave(@RequestBody List<AuthorDetailsDTO> dtos) {
        return authorDetailsService.saveAll(dtos);
    }

    /**
     * Batch delete multiple author details records by their IDs.
     *
     * @param authorIds list of author IDs to delete
     * @return 204 NO CONTENT on successful deletion
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@RequestBody List<Integer> authorIds) {
        authorDetailsService.deleteAllById(authorIds);
    }
}