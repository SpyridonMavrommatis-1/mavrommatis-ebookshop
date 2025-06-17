package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing the many-to-many relationship between {@code Author} and {@code Book}.
 *
 * <p>
 *     This controller exposes CRUD-like operations for the {@code author_book} join table,
 *     which represents a composite association between existing authors and books in the system.
 *     It does not contain business data beyond relational links, making it a "pure association" resource.
 * </p>
 *
 * <p>
 *     All operations are performed using DTOs such as {@link AuthorBookRequestDTO} and {@link AuthorBookResponseDTO},
 *     in order to abstract away JPA entity structures and enforce clean separation between layers.
 * </p>
 *
 * <h3>Authorization Rules</h3>
 * <ul>
 *     <li>{@code CUSTOMER} and {@code EMPLOYEE} roles can read associations via {@code GET} endpoints.</li>
 *     <li>{@code EMPLOYEE} and {@code ADMIN} roles can create new links between authors and books.</li>
 *     <li>Only the {@code ADMIN} role may delete existing associations, to prevent unintentional data loss.</li>
 * </ul>
 *
 * <p>
 *     All security constraints are enforced declaratively using {@code @PreAuthorize} annotations,
 *     aligning with the application's global role-based access control (RBAC) strategy.
 * </p>
 *
 * @see AuthorBookRequestDTO
 * @see AuthorBookResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity
 */
@RestController
@RequestMapping("/api/author-books")
public class AuthorBookRestController {

    private final AuthorBookService authorBookService;

    @Autowired
    public AuthorBookRestController(AuthorBookService authorBookService) {
        this.authorBookService = authorBookService;
    }

    /**
     * Retrieve all author-book associations.
     *
     *<p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @return a list of AuthorBookResponseDTO
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorBookResponseDTO> getAll() {
        return authorBookService.findAll();
    }

    /**
     * Retrieve a specific author-book association by composite key.
     *
     *<p>
     *     Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     *
     * @param authorId the author’s ID
     * @param bookId   the book’s ID
     * @return the matching AuthorBookResponseDTO
     */
    @GetMapping("/{authorId}/{bookId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public AuthorBookResponseDTO getById(
            @PathVariable Integer authorId,
            @PathVariable Integer bookId
    ) {
        return authorBookService.findById(authorId, bookId);
    }

    /**
     * Connect an existing author with an existing book.
     *<p>
     * Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     *
     * <p>
     * Ensures both entities exist before creating the association.
     * </p>
     *
     * @param request the composite key DTO of author and book
     * @return the created AuthorBookResponseDTO
     */
    @PostMapping("/connect")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public AuthorBookResponseDTO connect(@RequestBody AuthorBookRequestDTO request) {
        return authorBookService.connect(request);
    }

    /**
     * Connect multiple author-book associations in batch.
     *
     * <p>
     * Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * <p>
     * Validates each pair, persists the link only if both Author and Book exist,
     * and returns the list of created associations.
     * </p>
     *
     * @param requests list of composite key DTOs to connect
     * @return list of created AuthorBookResponseDTO
     */
    @PostMapping("/connect/batch")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('EMPLOYEE','ADMIN')")
    public List<AuthorBookResponseDTO> connectAll(@RequestBody List<AuthorBookRequestDTO> requests) {
        return authorBookService.connectAll(requests);
    }

    /**
     * Delete a specific author-book association.
     *
     * <p>
     *     Accessible to {@code ADMIN} role.
     * </p>
     *
     * @param authorId the author’s ID
     * @param bookId   the book’s ID
     */
    @DeleteMapping("/{authorId}/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void delete(
            @PathVariable Integer authorId,
            @PathVariable Integer bookId
    ) {
        authorBookService.deleteById(authorId, bookId);
    }

    /**
     * Delete multiple author-book associations in batch.
     *
     * <p>
     *    Accessible to {@code ADMIN} role.
     * </p>
     *
     * @param requests list of composite key DTOs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteBatch(@RequestBody List<AuthorBookRequestDTO> requests) {
        authorBookService.deleteAll(requests);
    }
}
