package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.BookReviewsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookReviewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing book reviews.
 * <p>
 * Handles CRUD and batch operations, enforces ownership rules,
 * and leverages a mapper to decouple DTOs from entities.
 * </p>
 */
@Service
public class BookReviewsServiceImpl implements BookReviewsService {

    private final BookReviewsRepository repository;
    private final BookReviewsMapper mapper;

    /**
     * Constructor for dependency injection.
     *
     * @param repository BookReviews JPA repository
     * @param mapper     Mapper between entity and DTO layers
     */
    @Autowired
    public BookReviewsServiceImpl(BookReviewsRepository repository,
                                  BookReviewsMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retrieves the ID of the currently authenticated user.
     * Assumes the username is the numeric customer ID.
     *
     * @return the authenticated customer's ID
     */
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Integer.parseInt(authentication.getName()); // assumes username = customerId
    }

    /**
     * Validates that the given customer ID matches the authenticated user.
     *
     * @param reviewCustomerId the customer ID from the review
     */
    private void checkOwnership(Integer reviewCustomerId) {
        Integer currentUserId = getCurrentUserId();
        if (!Objects.equals(currentUserId, reviewCustomerId)) {
            throw new AccessDeniedException("You can only modify your own reviews.");
        }
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
     * <p>
     * Enforces that the customerId in the DTO matches the authenticated user.
     * </p>
     */
    @Override
    @Transactional
    public BookReviewsResponseDTO save(BookReviewsRequestDTO dto) {
        Integer currentUserId = getCurrentUserId();
        if (!Objects.equals(dto.getCustomerId(), currentUserId)) {
            throw new AccessDeniedException("You can only create reviews for yourself.");
        }
        BookReviewsEntity entity = mapper.toEntity(dto);
        BookReviewsEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Enforces that only the review owner can perform updates.
     * </p>
     */
    @Override
    @Transactional
    public BookReviewsResponseDTO update(Integer id, BookReviewsRequestDTO dto) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot update. Review not found: " + id));

        checkOwnership(existing.getCustomer().getCustomerId());

        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());

        BookReviewsEntity updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Only the owner can delete their review.
     * </p>
     */
    @Override
    public void deleteById(Integer id) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found: " + id));

        checkOwnership(existing.getCustomer().getCustomerId());

        repository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates ownership and uniqueness before saving reviews in batch.
     * </p>
     */
    @Override
    @Transactional
    public List<BookReviewsResponseDTO> saveAll(List<BookReviewsRequestDTO> dtos) {
        Integer currentUserId = getCurrentUserId();

        for (BookReviewsRequestDTO dto : dtos) {
            if (!Objects.equals(dto.getCustomerId(), currentUserId)) {
                throw new AccessDeniedException("You can only batch-create reviews for yourself.");
            }
            if (repository.existsByBook_bookIdAndCustomer_customerId(dto.getBookId(), dto.getCustomerId())) {
                throw new RuntimeException(
                        "Cannot create review: customer " + dto.getCustomerId() +
                                " has already reviewed book " + dto.getBookId()
                );
            }
        }

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
     * <p>
     * Admin-only batch deletion.
     * </p>
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
