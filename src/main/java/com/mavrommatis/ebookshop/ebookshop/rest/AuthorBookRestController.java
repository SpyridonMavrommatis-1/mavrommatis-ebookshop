package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing the many-to-many relationship between Authors and Books.
 * <p>
 * All operations use DTOs to decouple the API from JPA entities.
 * </p>
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
     * @return a list of AuthorBookResponseDTO
     */
    @GetMapping
    public List<AuthorBookResponseDTO> getAll() {
        return authorBookService.findAll();
    }

    /**
     * Retrieve a specific author-book association by composite key.
     *
     * @param authorId the author’s ID
     * @param bookId   the book’s ID
     * @return the matching AuthorBookResponseDTO
     */
    @GetMapping("/{authorId}/{bookId}")
    public AuthorBookResponseDTO getById(
            @PathVariable Integer authorId,
            @PathVariable Integer bookId
    ) {
        return authorBookService.findById(authorId, bookId);
    }

    /**
     * Connect an existing author with an existing book.
     * <p>
     * Ensures both entities exist before creating the association.
     * </p>
     *
     * @param request the composite key DTO of author and book
     * @return the created AuthorBookResponseDTO
     */
    @PostMapping("/connect")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorBookResponseDTO connect(@RequestBody AuthorBookRequestDTO request) {
        return authorBookService.connect(request);
    }

    /**
     * Connect multiple author-book associations in batch.
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
    public List<AuthorBookResponseDTO> connectAll(@RequestBody List<AuthorBookRequestDTO> requests) {
        return authorBookService.connectAll(requests);
    }

    /**
     * Delete a specific author-book association.
     *
     * @param authorId the author’s ID
     * @param bookId   the book’s ID
     */
    @DeleteMapping("/{authorId}/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Integer authorId,
            @PathVariable Integer bookId
    ) {
        authorBookService.deleteById(authorId, bookId);
    }

    /**
     * Delete multiple author-book associations in batch.
     *
     * @param requests list of composite key DTOs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBatch(@RequestBody List<AuthorBookRequestDTO> requests) {
        authorBookService.deleteAll(requests);
    }
}
