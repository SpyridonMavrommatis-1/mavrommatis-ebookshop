package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.basic.AuthorBookRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.basic.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.basic.BookRepository;
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
 * Ensures that only existing authors and books can be linked.
 * Uses DTOs for all client communication and handles missing associations via exception.
 * </p>
 */
@Service
public class AuthorBookServiceImpl implements AuthorBookService {

    /**
     * Repository for handling persistence of author-book associations.
     * Used for retrieving and persisting {@link AuthorBookEntity} instances.
     */
    private final AuthorBookRepository repository;

    /**
     * Mapper responsible for converting between {@link AuthorBookEntity} and {@link AuthorBookResponseDTO}.
     * This is used extensively here because the service is currently read-only and only performs data transformation.
     * In read-heavy services with simple logic, the mapper becomes the dominant utility.
     */
    private final AuthorBookMapper mapper;

    /**
     * Repository for managing {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity} instances.
     * Although injected, it is currently unused since the service does not create or validate author entities.
     * Will become necessary if association creation is added in the future.
     */
    private final AuthorRepository authorRepository;

    /**
     * Repository for managing {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity} instances.
     * Currently unused, similar to the author repository, but retained for future write operations.
     */
    private final BookRepository bookRepository;

    /**
     * Constructs the service with all required dependencies.
     *
     * @param repository repository for AuthorBookEntity
     * @param mapper mapper to convert between entity and DTO
     * @param authorRepository repository for Author entities
     * @param bookRepository repository for Book entities
     */
    @Autowired
    public AuthorBookServiceImpl(AuthorBookRepository repository,
                                 AuthorBookMapper mapper,
                                 AuthorRepository authorRepository,
                                 BookRepository bookRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Fetch all author-book relationships.
     *
     * @return list of AuthorBookResponseDTOs
     */
    @Override
    public List<AuthorBookResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Fetch a specific author-book relationship using composite key.
     *
     * @param authorId author ID
     * @param bookId book ID
     * @return matching AuthorBookResponseDTO
     * @throws RuntimeException if not found
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
