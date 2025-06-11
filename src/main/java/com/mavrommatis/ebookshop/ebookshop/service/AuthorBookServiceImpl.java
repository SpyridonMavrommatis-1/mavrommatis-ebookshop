package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorBookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookIdEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link AuthorBookService}, handling CRUD operations
 * for author-book relationships via {@link AuthorBookMapper}.
 */
@Service
public class AuthorBookServiceImpl implements AuthorBookService {

    private final AuthorBookRepository repository;
    private final AuthorBookMapper mapper;

    /**
     * Constructs a new AuthorBookServiceImpl with required dependencies.
     *
     * @param repository repository for AuthorBookEntity persistence
     * @param mapper     mapper for converting between DTOs and entities
     */
    @Autowired
    public AuthorBookServiceImpl(AuthorBookRepository repository,
                                 AuthorBookMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    @Override
    public List<AuthorBookResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorBookResponseDTO findById(Integer authorId, Integer bookId) {
        AuthorBookIdEntity id = new AuthorBookIdEntity(authorId, bookId);
        AuthorBookEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Association not found: authorId=" + authorId + ", bookId=" + bookId));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    public AuthorBookResponseDTO save(AuthorBookRequestDTO dto) {
        AuthorBookEntity entity = mapper.toEntity(dto);
        AuthorBookEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    public void deleteById(Integer authorId, Integer bookId) {
        AuthorBookIdEntity id = new AuthorBookIdEntity(authorId, bookId);
        if (!repository.existsById(id)) {
            throw new RuntimeException(
                    "Association not found: authorId=" + authorId + ", bookId=" + bookId);
        }
        repository.deleteById(id);
    }

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
