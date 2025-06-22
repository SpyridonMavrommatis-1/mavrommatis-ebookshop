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

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    private final AuthorService authorService;

    @Autowired
    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Integer authorId) {
        AuthorResponseDTO dto = authorService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO created = authorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorResponseDTO update(@PathVariable Integer authorId, @Valid @RequestBody AuthorRequestDTO request) {
        return authorService.update(authorId, request);
    }

    @DeleteMapping("/{authorId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer authorId) {
        authorService.deleteById(authorId);
    }

    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorResponseDTO> batchSave(@Valid @RequestBody List<AuthorRequestDTO> requests) {
        return requests.stream()
                .map(authorService::save)
                .toList();
    }

    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> ids) {
        authorService.deleteAllById(ids);
    }
}