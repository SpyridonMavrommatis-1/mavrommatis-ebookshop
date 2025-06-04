package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;

    // Constructor injection
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Retrieve all books from the database
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    // Find a book by ID and return an Optional (could be empty)
    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    // Save a new book if it doesn't already exist
    @Override
    public Book save(Book book) {
        if (book.getBookId() != 0 && bookRepository.existsById(book.getBookId())) {
            throw new RuntimeException("Book already exists with id: " + book.getBookId());
        }
        return bookRepository.save(book);
    }

    // Save a list of books, checking each for existence
    @Override
    public List<Book> saveAll(List<Book> books) {
        for (Book book : books) {
            if (book.getBookId() != 0 && bookRepository.existsById(book.getBookId())) {
                throw new RuntimeException("Book already exists with id: " + book.getBookId());
            }
        }
        return bookRepository.saveAll(books);
    }

    // Delete a book by ID if it exists, otherwise throw exception
    @Override
    public void deleteById(Integer id) {

        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    // Delete all books by ID if they exist, otherwise throw exception
    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (bookRepository.existsById(id)) {
                bookRepository.deleteById(id);
            } else {
                throw new RuntimeException("Book not found with id: " + id);
            }
        }
    }
}
