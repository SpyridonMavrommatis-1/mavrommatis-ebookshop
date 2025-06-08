package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link BookService} interface.
 * Provides business logic and interaction with the BookRepository.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Constructor-based injection of {@link BookRepository}.
     *
     * @param bookRepository the repository used to access the books
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve all books from the database.
     *
     * @return a list of all {@link Book} entities
     */
    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieve a book by its ID.
     *
     * @param id the ID of the book
     * @return an {@link Optional} containing the book if found, or empty otherwise
     */
    @Override
    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    /**
     * Save a new book if it doesn't already exist in the database.
     *
     * @param book the book entity to save
     * @return the saved {@link Book}
     * @throws RuntimeException if the book already exists
     */
    @Override
    public Book save(Book book) {
        if (book.getBookId() != 0 && bookRepository.existsById(book.getBookId())) {
            throw new RuntimeException("Book already exists with id: " + book.getBookId());
        }
        return bookRepository.save(book);
    }

    /**
     * Save multiple books at once, checking each for existence.
     *
     * @param books a list of books to save
     * @return the saved list of books
     * @throws RuntimeException if any book already exists
     */
    @Override
    @Transactional
    public List<Book> saveAll(List<Book> books) {
        for (Book book : books) {
            if (book.getBookId() != 0 && bookRepository.existsById(book.getBookId())) {
                throw new RuntimeException("Book already exists with id: " + book.getBookId());
            }
        }
        return bookRepository.saveAll(books);
    }

    /**
     * Delete a book by its ID if it exists.
     *
     * @param id the ID of the book to delete
     * @throws RuntimeException if the book is not found
     */
    @Override
    public void deleteById(Integer id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    /**
     * Delete multiple books by their IDs if they exist.
     *
     * @param ids a list of book IDs to delete
     * @throws RuntimeException if any of the books are not found
     */
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