package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for {@link BookService}, handling business logic
 * and mapping between DTOs and entities via {@link BookMapper}.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    /**
     * Constructor for dependency injection.
     *
     * @param bookRepository    repository for persisting books
     * @param authorRepository  repository for fetching authors
     * @param bookMapper        mapper for converting between DTOs and entities
     */
    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           BookMapper bookMapper) {
        this.bookRepository   = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper       = bookMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookResponseDTO findById(Integer id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
        return bookMapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
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
                .orElseThrow(() -> new RuntimeException("Author not found: " + dto.getAuthorId()));
        book.setAuthor(author);

        BookEntity saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookResponseDTO update(Integer id, BookRequestDTO dto) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Cannot update. Book not found: " + id);
        }
        BookEntity existing = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found: " + id));
        bookMapper.updateEntity(dto, existing);
        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found: " + dto.getAuthorId()));
        existing.setAuthor(author);

        BookEntity updated = bookRepository.save(existing);
        return bookMapper.toResponse(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!bookRepository.existsById(id)) {
                throw new RuntimeException("Book not found: " + id);
            }
        }
        bookRepository.deleteAllById(ids);
    }
}