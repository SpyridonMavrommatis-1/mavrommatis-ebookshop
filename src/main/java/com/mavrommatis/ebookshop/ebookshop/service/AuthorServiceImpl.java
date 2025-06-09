package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing {@link AuthorEntity} entities.
 * Delegates database operations to {@link AuthorRepository}.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Constructor-based injection of AuthorRepository.
     *
     * @param authorRepository the repository to access Author data.
     */
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorEntity> findAll() {
        return authorRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorEntity> findById(Integer id) {
        return authorRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorEntity save(AuthorEntity author) {
        if (author.getAuthorId() != 0 && authorRepository.existsById(author.getAuthorId())) {
            throw new RuntimeException("Author already exists with id: " + author.getAuthorId());
        }
        return authorRepository.save(author);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorEntity> saveAll(List<AuthorEntity> authors) {
        for (AuthorEntity author : authors) {
            if (author.getAuthorId() != 0 && authorRepository.existsById(author.getAuthorId())) {
                throw new RuntimeException("Author already exists with id: " + author.getAuthorId());
            }
        }
        return authorRepository.saveAll(authors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        Optional<AuthorEntity> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Author not found with id: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
            } else {
                throw new RuntimeException("Author not found with id: " + id);
            }
        }
    }
}
