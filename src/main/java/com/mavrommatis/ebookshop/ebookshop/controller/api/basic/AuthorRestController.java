package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     REST controller responsible for managing authors
 *     using {@link AuthorRequestDTO} and {@link AuthorResponseDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity AutorEntity},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO
 * @see com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.basic.AuthorService
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
     *<p>
     *    Accessible to {@code CUSTOMER} {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @return 200 OK with a list of {@link AuthorResponseDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> findAll() {
        return authorService.findAll();
    }

    /**
     * Retrieve a specific author by ID.
     *
     * <p>
     *      Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     *
     * @param authorId the ID of the author to retrieve
     * @return 200 OK with the {@link AuthorResponseDTO} if found,
     *         or 404 NOT FOUND if no such author exists
     */
    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Integer authorId) {
        AuthorResponseDTO dto = authorService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new author.
     *<p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @param request the {@link AuthorRequestDTO} containing author data
     * @return 201 CREATED with the created {@link AuthorResponseDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> create(@RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO created = authorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing author.
     *<p>
     *      Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @param authorId the ID of the author to update
     * @param request  the {@link AuthorRequestDTO} with updated data
     * @return 200 OK with the updated {@link AuthorResponseDTO}
     */
    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorResponseDTO update(
            @PathVariable Integer authorId,
            @RequestBody AuthorRequestDTO request
    ) {
        return authorService.update(authorId, request);
    }

    /**
     * Deletes an author by its ID.
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
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    /**
     * Save multiple authors in batch.
     * <p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     *
     * @param requests list of {@link AuthorRequestDTO} entries to save
     * @return list of created {@link AuthorResponseDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> batchSave(@RequestBody List<AuthorRequestDTO> requests) {
        return requests.stream()
                .map(authorService::save)
                .toList();
    }

    /**
     * Deletes multiple authors by their IDs in a single batch operation.
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
     * @param ids list of author identifiers to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
    }
}
