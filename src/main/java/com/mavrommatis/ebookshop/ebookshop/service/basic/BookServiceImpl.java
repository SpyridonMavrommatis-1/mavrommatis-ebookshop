package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.basic.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.basic.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.book.AuthorNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.exception.book.BookNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing Book entities and their associations.
 * <p>
 * Handles CRUD operations for books, including their associated author and details information.
 * Converts between {@link BookEntity} and {@link BookResponseDTO} using {@link BookMapper}.
 * </p>
 */
@Service
public class BookServiceImpl implements BookService {

    /**
     * Repository for managing book persistence.
     */
    private final BookRepository bookRepository;

    /**
     * Repository for retrieving associated authors.
     */
    private final AuthorRepository authorRepository;

    /**
     * Mapper for converting between DTOs and book entities.
     */
    private final BookMapper bookMapper;

    /**
     * Constructs a new BookServiceImpl.
     *
     * @param bookRepository   repository for books
     * @param authorRepository repository for authors
     * @param bookMapper       mapper for entity-DTO conversion
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Retrieves all books.
     *
     * @return list of all books as DTOs
     */
    @Override
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id the ID of the book
     * @return the corresponding BookResponseDTO
     * @throws BookNotFoundException if not found
     */
    @Override
    public BookResponseDTO findById(Integer id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toResponse(entity);
    }

    /**
     * Creates a new book record.
     *
     * @param dto the book data to save
     * @return the saved BookResponseDTO
     * @throws AuthorNotFoundException if the specified author does not exist
     */
    @Override
    @Transactional
    public BookResponseDTO save(BookRequestDTO dto) {
        BookEntity book = bookMapper.toEntity(dto);

        if (dto.getDetails() != null) {
            BookDetailsEntity details = bookMapper.bookDetailsDtoToEntity(dto.getDetails());
            details.setBook(book);
            book.setBookDetails(details);
        }

        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(dto.getAuthorId()));
        book.setAuthor(author);

        BookEntity saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    /**
     * Updates an existing book.
     *
     * @param id  the ID of the book to update
     * @param dto the updated data
     * @return the updated BookResponseDTO
     * @throws BookNotFoundException   if the book does not exist
     * @throws AuthorNotFoundException if the author does not exist
     */
    @Override
    @Transactional
    public BookResponseDTO update(Integer id, BookRequestDTO dto) {
        BookEntity existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookMapper.updateEntity(dto, existing);

        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(dto.getAuthorId()));
        existing.setAuthor(author);

        BookEntity updated = bookRepository.save(existing);
        return bookMapper.toResponse(updated);
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID to delete
     * @throws BookNotFoundException if the book does not exist
     */
    @Override
    public void deleteById(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * Deletes multiple books by their IDs.
     *
     * @param ids list of IDs to delete
     * @throws BookNotFoundException if any ID does not exist
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!bookRepository.existsById(id)) {
                throw new BookNotFoundException(id);
            }
        }
        bookRepository.deleteAllById(ids);
    }
}
