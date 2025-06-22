package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorBookRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.helper.AuthorBookIdEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
