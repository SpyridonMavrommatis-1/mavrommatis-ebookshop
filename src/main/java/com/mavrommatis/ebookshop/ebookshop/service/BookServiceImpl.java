package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
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

    private final AuthorService authorService;

    /**
     * Constructor-based injection of dependencies.
     *
     * @param bookRepository the repository used to access books
     * @param authorService the service used to manage authors
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
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
     * Updates an existing {@link Book} in the database, ensuring safe merging of nested entities.
     * <p>
     * This method retrieves the managed instance of the book from the database, then updates its fields
     * and relationships (such as {@link BookDetails} and {@link Author}) carefully, avoiding conflicts
     * caused by multiple objects with the same identifier in the persistence context.
     *
     * @param book the {@link Book} entity containing updated values (from client input)
     * @return the updated and saved {@link Book} entity
     * @throws RuntimeException if the book or the associated author does not exist
     */
    @Override
    public Book update(Book book) {
        int bookId = book.getBookId();

        if (bookId == 0 || !bookRepository.existsById(bookId)) {
            throw new RuntimeException("Cannot update. Book not found with id: " + bookId);
        }

        // Fetch the managed Book entity from the database
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Update primitive fields of Book
        existingBook.setTitle(book.getTitle());
        existingBook.setLanguage(book.getLanguage());
        existingBook.setGenre(book.getGenre());
        existingBook.setLiteraryForm(book.getLiteraryForm());

        // Handle BookDetails relationship carefully to avoid NonUniqueObjectException
        BookDetails newDetails = book.getBookDetails();
        if (newDetails != null) {
            BookDetails managedDetails = existingBook.getBookDetails();
            if (managedDetails == null) {
                newDetails.setBook(existingBook);
                existingBook.setBookDetails(newDetails);
            } else {
                managedDetails.setSummary(newDetails.getSummary());
                managedDetails.setIsbn(newDetails.getIsbn());
                managedDetails.setPages(newDetails.getPages());
                managedDetails.setDimensions(newDetails.getDimensions());
                managedDetails.setCoverType(newDetails.getCoverType());
                managedDetails.setWeight(newDetails.getWeight());
                managedDetails.setPublishDate(newDetails.getPublishDate());
            }
        }

        // Handle Author relationship
        if (book.getAuthor() != null && book.getAuthor().getAuthorId() != 0) {
            Author managedAuthor = authorService.findById(book.getAuthor().getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + book.getAuthor().getAuthorId()));
            existingBook.setAuthor(managedAuthor);
            managedAuthor.addBook(existingBook); // maintain bidirectional consistency
        }

        // Save the updated Book
        return bookRepository.save(existingBook);
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