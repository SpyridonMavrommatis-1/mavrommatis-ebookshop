package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing authors via DTOs.
 * <p>
 * Exposes endpoints for CRUD and batch operations on {@link AuthorRequestDTO}
 * and returns {@link AuthorResponseDTO}.
 * All endpoints consume and produce JSON.
 * </p>
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    /**
     * Constructor-based injection of the author service.
     *
     * @param authorService the service handling author business logic
     */
    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieve all authors.
     *
     * @return 200 OK with a list of {@link AuthorResponseDTO}
     */
    @GetMapping
    public List<AuthorResponseDTO> findAll() {
        return authorService.findAll();
    }

    /**
     * Retrieve a specific author by ID.
     *
     * @param authorId the ID of the author to retrieve
     * @return 200 OK with the {@link AuthorResponseDTO} if found,
     *         or 404 NOT FOUND if no such author exists
     */
    @GetMapping("/{authorId}")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Integer authorId) {
        AuthorResponseDTO dto = authorService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new author.
     *
     * @param request the {@link AuthorRequestDTO} containing author data
     * @return 201 CREATED with the created {@link AuthorResponseDTO}
     */
    @PostMapping
    public ResponseEntity<AuthorResponseDTO> create(@RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO created = authorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing author.
     *
     * @param authorId the ID of the author to update
     * @param request  the {@link AuthorRequestDTO} with updated data
     * @return 200 OK with the updated {@link AuthorResponseDTO}
     */
    @PutMapping("/{authorId}")
    public AuthorResponseDTO update(
            @PathVariable Integer authorId,
            @RequestBody AuthorRequestDTO request
    ) {
        return authorService.update(authorId, request);
    }

    /**
     * Delete an author by ID.
     *
     * @param authorId the ID of the author to delete
     * @return 204 NO CONTENT on successful deletion
     */
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    /**
     * Batch create multiple authors.
     *
     * @param requests list of {@link AuthorRequestDTO} to create
     * @return 200 OK with a list of created {@link AuthorResponseDTO}
     */
    @PostMapping("/batch")
    public List<AuthorResponseDTO> batchSave(@RequestBody List<AuthorRequestDTO> requests) {
        return requests.stream()
                .map(authorService::save)
                .toList();
    }

    /**
     * Batch delete multiple authors by their IDs.
     *
     * @param ids list of author IDs to delete
     * @return 204 NO CONTENT on successful deletion
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
    }
}
