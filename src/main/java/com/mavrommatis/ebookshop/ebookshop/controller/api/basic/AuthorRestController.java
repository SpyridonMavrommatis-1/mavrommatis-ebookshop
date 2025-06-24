package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@code Author} resources.
 * <p>
 * Provides endpoints for basic CRUD operations and batch handling.
 * Access is role-restricted using Spring Security.
 * </p>
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    /**
     * Constructs the controller with required service dependency.
     *
     * @param authorService the service handling author operations
     */
    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieves all authors in the system.
     * <p>Accessible by all authenticated roles.</p>
     *
     * @return list of {@link AuthorResponseDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> findAll() {
        return authorService.findAll();
    }

    /**
     * Retrieves a single author by ID.
     * <p>Accessible by all authenticated roles.</p>
     *
     * @param authorId the author's ID
     * @return the author data wrapped in a {@link ResponseEntity}
     */
    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Integer authorId) {
        AuthorResponseDTO dto = authorService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new author.
     * <p>Only accessible by EMPLOYEE or ADMIN roles.</p>
     *
     * @param request validated request body
     * @return the created author
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO created = authorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing author.
     * <p>Only accessible by EMPLOYEE or ADMIN roles.</p>
     *
     * @param authorId the ID of the author to update
     * @param request  validated update data
     * @return the updated author
     */
    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorResponseDTO update(@PathVariable Integer authorId, @Valid @RequestBody AuthorRequestDTO request) {
        return authorService.update(authorId, request);
    }

    /**
     * Deletes an author by ID.
     * <p>Only accessible by ADMIN role.</p>
     *
     * @param authorId the ID of the author to delete
     */
    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    /**
     * Saves multiple authors in a single request.
     * <p>Only accessible by EMPLOYEE or ADMIN roles.</p>
     *
     * @param requests list of author DTOs to create
     * @return list of created authors
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> batchSave(@Valid @RequestBody List<AuthorRequestDTO> requests) {
        return requests.stream()
                .map(authorService::save)
                .toList();
    }

    /**
     * Deletes multiple authors by their IDs.
     * <p>Only accessible by ADMIN role.</p>
     *
     * @param ids list of author IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
    }
}
