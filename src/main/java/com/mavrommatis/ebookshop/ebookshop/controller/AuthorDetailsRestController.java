package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authordetails")
public class AuthorDetailsRestController {

    private final AuthorDetailsService authorDetailsService;

    @Autowired
    public AuthorDetailsRestController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    // GET /api/authordetails
    @GetMapping
    public List<AuthorDetails> findAll() {
        return authorDetailsService.findAll();
    }

    // GET /api/authordetails/{id}
    @GetMapping("/{id}")
    public AuthorDetails findById(@PathVariable int id) {
        return authorDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found with id: " + id));
    }

    // POST /api/authordetails
    @PostMapping
    public AuthorDetails createAuthorDetails(@RequestBody AuthorDetails authorDetails) {
        return authorDetailsService.save(authorDetails);
    }

    // PUT /api/authordetails/{id}
    @PutMapping("/{id}")
    public AuthorDetails updateAuthorDetails(@PathVariable int id, @RequestBody AuthorDetails authorDetails) {
        authorDetails.setAuthorId(id);
        return authorDetailsService.save(authorDetails);
    }

    // POST /api/authordetails/batch
    @PostMapping("/batch")
    public List<AuthorDetails> saveAllAuthorDetails(@RequestBody List<AuthorDetails> authorDetailsList) {
        return authorDetailsService.saveAll(authorDetailsList);
    }

    // DELETE /api/authordetails/{id}
    @DeleteMapping("/{id}")
    public String deleteAuthorDetails(@PathVariable int id) {
        authorDetailsService.deleteById(id);
        return "AuthorDetails with id " + id + " deleted.";
    }

    // DELETE /api/authordetails/batch
    @DeleteMapping("/batch")
    public String deleteAllAuthorDetails(@RequestBody List<Integer> ids) {
        authorDetailsService.deleteAllById(ids);
        return "AuthorDetails with ids " + ids + " deleted.";
    }
}
