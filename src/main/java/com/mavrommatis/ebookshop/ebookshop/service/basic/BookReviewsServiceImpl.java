package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.BookReviewsRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.review.DuplicateReviewException;
import com.mavrommatis.ebookshop.ebookshop.exception.review.ReviewNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.exception.review.UnauthorizedReviewActionException;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookReviewsMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookReviewsServiceImpl implements BookReviewsService {

    private final BookReviewsRepository repository;
    private final BookReviewsMapper mapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public BookReviewsServiceImpl(BookReviewsRepository repository,
                                  BookReviewsMapper mapper,
                                  CustomerRepository customerRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerRepository = customerRepository;
    }

    private Integer getCurrentUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new ReviewNotFoundException("Customer not found: " + username))
                .getCustomerId();
    }

    @Override
    public List<BookReviewsResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookReviewsResponseDTO findById(Integer id) {
        BookReviewsEntity entity = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found: " + id));
        return mapper.toResponse(entity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('CUSTOMER')")
    public BookReviewsResponseDTO save(BookReviewsRequestDTO dto) {
        Integer currentUserId = getCurrentUserId();

        if (repository.existsByBook_bookIdAndCustomer_customerId(dto.getBookId(), currentUserId)) {
            throw new DuplicateReviewException("You already reviewed this book.");
        }

        BookReviewsEntity entity = mapper.toEntity(dto);
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerId(currentUserId);
        entity.setCustomer(customer);

        BookReviewsEntity saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public BookReviewsResponseDTO update(Integer id, BookReviewsRequestDTO dto) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Cannot update. Review not found: " + id));

        if (!Objects.equals(getCurrentUserId(), existing.getCustomer().getCustomerId())) {
            throw new UnauthorizedReviewActionException("You can only update your own reviews.");
        }

        existing.setRating(dto.getRating());
        existing.setComment(dto.getComment());

        BookReviewsEntity updated = repository.save(existing);
        return mapper.toResponse(updated);
    }

    @Override
    public void deleteById(Integer id) {
        BookReviewsEntity existing = repository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found: " + id));

        if (!Objects.equals(getCurrentUserId(), existing.getCustomer().getCustomerId())) {
            throw new UnauthorizedReviewActionException("You can only delete your own reviews.");
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repository.existsById(id)) {
                throw new ReviewNotFoundException("Review not found: " + id);
            }
        }
        repository.deleteAllById(ids);
    }
}