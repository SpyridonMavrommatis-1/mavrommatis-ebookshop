package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents additional details about a Book, such as physical attributes and metadata.
 * <p>
 * This entity is in a one-to-one relationship with {@link BookEntity}, sharing the same primary key.
 */
@Entity
@Table(name = "book_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "book")
public class BookDetailsEntity {

    /**
     * Shared primary key with Book entity.
     * This ID is not generated independently; it's derived from the associated Book.
     */
    @Id
    @Column(name = "book_id")
    private Integer bookId;

    /**
     * Date of publication.
     */
    @Column(name = "publish_date")
    private LocalDate publishDate;

    /**
     * Total number of pages.
     */
    @Column(name = "pages")
    private int pages;

    /**
     * Summary of the book.
     * Stored as TEXT for longer descriptions.
     */
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    /**
     * Physical dimensions of the book (e.g., "20x15x3 cm").
     */
    @Column(name = "dimensions")
    private String dimensions;

    /**
     * Type of book cover (e.g., Hardcover, Paperback).
     */
    @Column(name = "cover_type")
    private String coverType;

    /**
     * Weight of the book in kilograms.
     */
    @Column(name = "weight")
    private BigDecimal weight;

    /**
     * Timestamp when the record was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the record was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The owning Book entity.
     * <p>
     * Uses lazy loading and avoids circular reference during serialization.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private BookEntity book;

    /**
     * Constructs a BookDetailsEntity with all descriptive fields.
     * The ID and timestamps are handled automatically.
     *
     * @param weight      the weight of the book
     * @param coverType   the type of cover
     * @param dimensions  the book's physical dimensions
     * @param summary     a brief summary of the book
     * @param pages       number of pages
     * @param publishDate publication date
     */
    public BookDetailsEntity(BigDecimal weight, String coverType, String dimensions,
                             String summary, int pages, LocalDate publishDate) {
        this.weight = weight;
        this.coverType = coverType;
        this.dimensions = dimensions;
        this.summary = summary;
        this.pages = pages;
        this.publishDate = publishDate;
    }

    /**
     * Automatically sets the creation timestamp before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Automatically sets the update timestamp before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
