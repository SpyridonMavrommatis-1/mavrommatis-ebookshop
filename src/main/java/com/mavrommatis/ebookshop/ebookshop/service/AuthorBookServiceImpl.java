package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorBookRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookIdEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing the many-to-many relationship between Authors and Books.
 * <p>
 * This implementation ensures that only existing authors and books can be linked;
 * it throws a RuntimeException (which can be translated to 404) if either side is missing
 * or if the association already exists.
 * All operations use DTOs to decouple the API from JPA entities.
 * </p>
 */
@Service
public class AuthorBookServiceImpl implements AuthorBookService {

    private final AuthorBookRepository repository;
    private final AuthorBookMapper     mapper;
    private final AuthorRepository     authorRepository;
    private final BookRepository       bookRepository;

    /**
     * Constructs a new AuthorBookServiceImpl with the necessary dependencies.
     *
     * @param repository        repository for AuthorBookEntity persistence
     * @param mapper            mapper for converting between DTOs and entities
     * @param authorRepository  repository for AuthorEntity lookup
     * @param bookRepository    repository for BookEntity lookup
     */
    @Autowired
    public AuthorBookServiceImpl(AuthorBookRepository repository,
                                 AuthorBookMapper     mapper,
                                 AuthorRepository     authorRepository,
                                 BookRepository       bookRepository) {
        this.repository       = repository;
        this.mapper           = mapper;
        this.authorRepository = authorRepository;
        this.bookRepository   = bookRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorBookResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorBookResponseDTO findById(Integer authorId, Integer bookId) {
        AuthorBookIdEntity id = new AuthorBookIdEntity(authorId, bookId);
        AuthorBookEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Association not found: authorId=" + authorId + ", bookId=" + bookId));
        return mapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates that both the author and the book exist before creating the link,
     * and that the association does not already exist.
     * </p>
     */
    @Override
    @Transactional
    public AuthorBookResponseDTO connect(AuthorBookRequestDTO dto) {
        // 1. Ensure author exists
        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException(
                        "Author not found: " + dto.getAuthorId()));

        // 2. Ensure book exists
        BookEntity book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException(
                        "Book not found: " + dto.getBookId()));

        // 3. Prevent duplicate association
        AuthorBookIdEntity pk = new AuthorBookIdEntity(dto.getAuthorId(), dto.getBookId());
        if (repository.existsById(pk)) {
            throw new RuntimeException(
                    "Association already exists: authorId=" + dto.getAuthorId() +
                            ", bookId=" + dto.getBookId());
        }

        // 4. Build & persist the association entity
        AuthorBookEntity entity = new AuthorBookEntity(author, book);
        AuthorBookEntity saved  = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates each pair in the list:
     * - both Author and Book must exist
     * - the association must not already exist
     * Persists the link if valid, and returns the list of created associations.
     * </p>
     */
    @Override
    @Transactional
    public List<AuthorBookResponseDTO> connectAll(List<AuthorBookRequestDTO> dtos) {
        return dtos.stream().map(dto -> {
            // validate existence
            AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException(
                            "Author not found: " + dto.getAuthorId()));
            BookEntity book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException(
                            "Book not found: " + dto.getBookId()));

            // prevent duplicate
            AuthorBookIdEntity pk = new AuthorBookIdEntity(dto.getAuthorId(), dto.getBookId());
            if (repository.existsById(pk)) {
                throw new RuntimeException(
                        "Association already exists: authorId=" + dto.getAuthorId() +
                                ", bookId=" + dto.getBookId());
            }

            // map & save
            AuthorBookEntity entity = new AuthorBookEntity(author, book);
            AuthorBookEntity saved  = repository.save(entity);
            return mapper.toResponse(saved);
        }).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer authorId, Integer bookId) {
        AuthorBookIdEntity id = new AuthorBookIdEntity(authorId, bookId);
        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Association not found: authorId=" + authorId + ", bookId=" + bookId);
        }
        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAll(List<AuthorBookRequestDTO> dtos) {
        for (AuthorBookRequestDTO dto : dtos) {
            AuthorBookIdEntity id = new AuthorBookIdEntity(dto.getAuthorId(), dto.getBookId());
            if (!repository.existsById(id)) {
                throw new RuntimeException(
                        "Association not found: authorId=" + dto.getAuthorId() + ", bookId=" + dto.getBookId());
            }
        }
        dtos.forEach(dto -> {
            AuthorBookIdEntity id = new AuthorBookIdEntity(dto.getAuthorId(), dto.getBookId());
            repository.deleteById(id);
        });
    }
}
