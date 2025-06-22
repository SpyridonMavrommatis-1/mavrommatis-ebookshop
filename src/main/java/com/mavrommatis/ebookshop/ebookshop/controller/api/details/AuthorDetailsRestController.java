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

@RestController
@RequestMapping("/api/author-details")
public class AuthorDetailsRestController {

    private final AuthorDetailsService authorDetailsService;

    @Autowired
    public AuthorDetailsRestController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> findAll() {
        return authorDetailsService.findAll();
    }

    @GetMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> findById(@PathVariable Integer authorId) {
        AuthorDetailsDTO dto = authorDetailsService.findById(authorId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public ResponseEntity<AuthorDetailsDTO> create(@Valid @RequestBody AuthorDetailsDTO dto) {
        AuthorDetailsDTO created = authorDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{authorId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public AuthorDetailsDTO update(@PathVariable Integer authorId, @Valid @RequestBody AuthorDetailsDTO dto) {
        return authorDetailsService.save(dto);
    }

    @DeleteMapping("/{authorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer authorId) {
        authorDetailsService.deleteById(authorId);
    }

    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AuthorDetailsDTO> batchSave(@Valid @RequestBody List<AuthorDetailsDTO> dtos) {
        return authorDetailsService.saveAll(dtos);
    }

    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> authorIds) {
        authorDetailsService.deleteAllById(authorIds);
    }
}
