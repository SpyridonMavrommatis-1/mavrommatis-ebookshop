package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookReviewsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookReviewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link BookReviewService}, handling CRUD operations
 * for book reviews via {@link BookReviewsMapper}.
 */
@Service
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewsRepository repository;
    private final BookReviewsMapper mapper;

    /**
     * Constructs a new BookReviewServiceImpl with required dependencies.
     *
     * @param repository repository for BookReviewsEntity persistence
     * @param mapper     mapper for converting between DTOs and entities
     */
    @Autowired
    public BookReviewServiceImpl(BookReviewsRepository repository,
                                 BookReviewsMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookReviewResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReviewResponseDTO findById(Integer id) {
        BookReviewsEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookReviewResponseDTO save(BookReviewRequestDTO dto) {
        BookReviewsEntity entity = mapper.toEntity(dto);
        BookReviewsEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookReviewResponseDTO update(Integer id, BookReviewRequestDTO dto) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update. Review not found: " + id));
        // Update fields manually
        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());
        BookReviewsEntity updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Review not found: " + id);
        }
        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repository.existsById(id)) {
                throw new RuntimeException("Review not found: " + id);
            }
        }
        repository.deleteAllById(ids);
    }
}
