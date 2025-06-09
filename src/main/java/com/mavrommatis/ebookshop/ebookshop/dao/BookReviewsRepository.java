package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.BookReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link BookReviewsEntity} persistence.
 * <p>
 * Provides CRUD operations for book reviews, such as saving new reviews,
 * retrieving reviews by ID, and deleting reviews.
 * </p>
 *
 * Example usage:
 * <pre>
 *     BookReviewsEntity review = bookReviewsRepository.findById(1).orElse(null);
 * </pre>
 *
 * @see BookReviewsEntity
 */
@Repository
public interface BookReviewsRepository extends JpaRepository<BookReviewsEntity, Integer> {
}
