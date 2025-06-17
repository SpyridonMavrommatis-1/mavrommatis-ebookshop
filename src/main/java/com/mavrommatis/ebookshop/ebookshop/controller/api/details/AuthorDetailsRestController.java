package com.mavrommatis.ebookshop.ebookshop.controller.api.details;

import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.details.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     REST controller responsible for managing author details
 *     using {@link AuthorDetailsDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity AuthorDetails},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.details.AuthorDetailsService
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
     * <p>
     *    Accessible to {@code CUSTOMER} {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @return a list of {@link AuthorDetailsDTO} for all authors
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> findAll() {
        return authorDetailsService.findAll();
    }

    /**
     * Retrieve a specific author details record by its ID.
     *
     * <p>
     *     Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param authorId the unique identifier of the author
     * @return 200 OK with the {@link AuthorDetailsDTO} if found,
     *         or 404 NOT FOUND if no record exists
     */
    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> findById(@PathVariable Integer authorId) {
        AuthorDetailsDTO dto = authorDetailsService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new author details record.
     *
     * <p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param dto the {@link AuthorDetailsDTO} containing new author profile data
     * @return 201 CREATED with the persisted {@link AuthorDetailsDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> create(
            @RequestBody AuthorDetailsDTO dto
    ) {
        AuthorDetailsDTO created = authorDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing author details record.
     *
     * <p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param authorId the ID of the author details to update
     * @param dto      the {@link AuthorDetailsDTO} containing updated profile data
     * @return 200 OK with the updated {@link AuthorDetailsDTO}
     */
    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorDetailsDTO update(
            @PathVariable Integer authorId,
            @RequestBody AuthorDetailsDTO dto
    ) {
        // The service layer handles upsert semantics
        return authorDetailsService.save(dto);
    }

    /**
     * Delete multiple author details by their book IDs.
     * <p>
     *     Accessible only to users with the {@code ADMIN} role.
     * </p>
     *
     * <p>
     *     This method returns an HTTP 204 (No Content) status code on success, indicating that:
     * </p>
     *
     * <ul>
     *     <li>the request was successfully processed,</li>
     *     <li>but no response body is returned to the client.</li>
     * </ul>
     * <p>
     *     The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *     instead of returning a {@code ResponseEntity<Void>} with status code 204.
     *     This keeps the controller method signature minimal and declarative.
     * </p>
     * @param authorId the unique identifier of the book to delete
     */
    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer authorId) {
        authorDetailsService.deleteById(authorId);
    }

    /**
     * Batch create or update multiple author details records.
     *
     * <p>
     *   Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @param dtos a list of {@link AuthorDetailsDTO} to persist
     * @return 200 OK with the list of persisted {@link AuthorDetailsDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> batchSave(@RequestBody List<AuthorDetailsDTO> dtos) {
        return authorDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple author details by their book IDs.
     * <p>
     * This endpoint is accessible only to users with the {@code ADMIN} role.
     * <p>
     * This method returns an HTTP 204 (No Content) status code on success, indicating that:
     *   <ul>
     *       <li>the request was successfully processed,</li>
     *       <li>but no response body is returned to the client.</li>
     *   </ul>
     * <p>
     *     The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *     instead of returning a {@code ResponseEntity<Void>} with status code 204.
     *     This keeps the controller method signature minimal and declarative.
     * </p>
     *
     * @param authorIds list of author identifiers to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> authorIds) {
        authorDetailsService.deleteAllById(authorIds);
    }
}