package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorRestController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorRestController.class);

    private final AuthorRepository authorRepository;

    public AuthorRestController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/api/authors")
    public List<Author> getAllAuthors(){
        logger.info("GET /api/books called");

        List<Author> authors = authorRepository.findAll();
        logger.debug("Number of authors fetched: {}", authors.size());

        return authors;
    }
}
