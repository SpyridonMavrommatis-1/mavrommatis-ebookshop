package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookReviewsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookReviewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link BookReviewsService}, handling CRUD operations
 * for book reviews via {@link BookReviewsMapper}.
 */
@Service
public class BookReviewsServiceImpl implements BookReviewsService {

    private final BookReviewsRepository repository;
    private final BookReviewsMapper mapper;

    /**
     * Constructs a new BookReviewServiceImpl with required dependencies.
     *
     * @param repository repository for BookReviewsEntity persistence
     * @param mapper     mapper for converting between DTOs and entities
     */
    @Autowired
    public BookReviewsServiceImpl(BookReviewsRepository repository,
                                  BookReviewsMapper mapper) {
        this.repository = repository;
        this.mapper     = mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookReviewsResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReviewsResponseDTO findById(Integer id) {
        BookReviewsEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));
        return mapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public BookReviewsResponseDTO save(BookReviewsRequestDTO dto) {
        BookReviewsEntity entity = mapper.toEntity(dto);
        BookReviewsEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Only the owner (same customerId) may update their review.
     * BookId and customerId in the request are ignored.
     * </p>
     */
    @Override
    @Transactional
    public BookReviewsResponseDTO update(Integer id, BookReviewsRequestDTO dto) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update. Review not found: " + id));

        // Ownership check
        if (existing.getCustomer().getCustomerId() != dto.getCustomerId()) {
            throw new RuntimeException("Cannot edit review: not the owner");
        }

        // Only rating/comment are updatable
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
     * <p>
     * Validates uniqueness of each review (one per customer/book) before saving.
     * Throws if any duplicate is found.
     */
    @Override
    @Transactional
    public List<BookReviewsResponseDTO> saveAll(List<BookReviewsRequestDTO> dtos) {
        // Προέλεγχος σε κάθε DTO
        for (BookReviewsRequestDTO dto : dtos) {
            if (repository.existsByBook_bookIdAndCustomer_customerId(dto.getBookId(), dto.getCustomerId())) {
                throw new RuntimeException(
                        "Cannot create review: customer " + dto.getCustomerId() +
                                " has already reviewed book " + dto.getBookId()
                );
            }
        }
        // Χαρτογράφηση → αποθήκευση → απάντηση
        List<BookReviewsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        List<BookReviewsEntity> saved = repository.saveAll(entities);

        return saved.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
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
