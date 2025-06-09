package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Represents a review written by a {@link CustomerEntity} for a {@link BookEntity}.
 * <p>
 * This entity is linked to both the customer who wrote the review and the book that was reviewed.
 * It also includes a numeric rating, an optional comment, and automatic timestamps for creation and update.
 */
@Entity
@Table(name = "book_reviews")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"book", "customer"})
public class BookReviewsEntity {

    /**
     * Unique identifier for each review.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId;

    /**
     * Reference to the reviewed book.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private BookEntity book;

    /**
     * Reference to the customer who submitted the review.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private CustomerEntity customer;

    /**
     * The numeric rating provided by the customer (required).
     */
    @Column(name = "rating", nullable = false)
    private int rating;

    /**
     * The optional text comment of the review.
     */
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    /**
     * Timestamp indicating when the review was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update to the review.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Sets the creation timestamp before persisting the review.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Sets the update timestamp before updating the review.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
