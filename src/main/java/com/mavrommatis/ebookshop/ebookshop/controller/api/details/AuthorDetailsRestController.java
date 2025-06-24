package com.mavrommatis.ebookshop.ebookshop.controller.api.details;

import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.details.AuthorDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing extended {@code AuthorDetails} data.
 * <p>
 * Provides role-restricted CRUD and batch endpoints for author details.
 * </p>
 */
@RestController
@RequestMapping("/api/author-details")
public class AuthorDetailsRestController {

    private final AuthorDetailsService authorDetailsService;

    /**
     * Constructs the controller with injected {@link AuthorDetailsService}.
     *
     * @param authorDetailsService the service handling author details operations
     */
    @Autowired
    public AuthorDetailsRestController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    /**
     * Retrieves all author details.
     * <p>Accessible by CUSTOMER, EMPLOYEE, and ADMIN roles.</p>
     *
     * @return list of {@link AuthorDetailsDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> findAll() {
        return authorDetailsService.findAll();
    }

    /**
     * Retrieves detailed information of a single author by ID.
     *
     * @param authorId the author's ID
     * @return author details as {@link AuthorDetailsDTO}
     */
    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> findById(@PathVariable Integer authorId) {
        AuthorDetailsDTO dto = authorDetailsService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Creates a new author details entry.
     * <p>Only accessible by EMPLOYEE or ADMIN roles.</p>
     *
     * @param dto the details data to create
     * @return created details
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> create(@Valid @RequestBody AuthorDetailsDTO dto) {
        AuthorDetailsDTO created = authorDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Updates an existing author details entry.
     * <p>Only accessible by EMPLOYEE or ADMIN roles.</p>
     *
     * @param authorId the ID of the author to update
     * @param dto      the updated details
     * @return updated {@link AuthorDetailsDTO}
     */
    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorDetailsDTO update(@PathVariable Integer authorId, @Valid @RequestBody AuthorDetailsDTO dto) {
        return authorDetailsService.save(dto);
    }

    /**
     * Deletes author details by ID.
     * <p>Only ADMIN role is allowed.</p>
     *
     * @param authorId the ID of the author to delete
     */
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer authorId) {
        authorDetailsService.deleteById(authorId);
    }

    /**
     * Creates multiple author details entries in batch.
     * <p>Only EMPLOYEE and ADMIN roles are allowed.</p>
     *
     * @param dtos list of {@link AuthorDetailsDTO} to create
     * @return list of created entries
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> batchSave(@Valid @RequestBody List<AuthorDetailsDTO> dtos) {
        return authorDetailsService.saveAll(dtos);
    }

    /**
     * Deletes multiple author details entries by ID in batch.
     * <p>Only ADMIN role is allowed.</p>
     *
     * @param authorIds list of author IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> authorIds) {
        authorDetailsService.deleteAllById(authorIds);
    }
}
