package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookRestController {

    private static final Logger logger = LoggerFactory.getLogger(BookRestController.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookRestController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        logger.info("BookRestController initialized");
    }

    @GetMapping("/api/books")
    public List<Book> getAllBooks(){
        logger.info("GET /api/books called");

        List<Book> books = bookRepository.findAll();
        logger.debug("Number of books fetched: {}", books.size());

        return books;
    }
}
