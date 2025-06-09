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
 * This class shares its primary key with the associated Book entity.
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
     * Also acts as foreign key due to @MapsId and @JoinColumn.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    /**
     * International Standard Book Number.
     */
    @Column(name = "isbn")
    private String isbn;

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
     * Physical dimensions of the book.
     */
    @Column(name = "dimensions")
    private String dimensions;

    /**
     * Type of book cover (e.g., Hardcover, Paperback).
     */
    @Column(name = "cover_type")
    private String coverType;

    /**
     * Weight of the book.
     */
    @Column(name = "weight")
    private BigDecimal weight;

    /**
     * Timestamp when the record is first persisted.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the record is last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Associated Book entity.
     * Uses LAZY loading and avoids circular reference during serialization.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private BookEntity book;


    /**
     * Constructs BookDetails with all descriptive fields except the ID and timestamps.
     * These are set automatically or by the owning Book entity.
     *
     * @param weight       the weight of the book
     * @param coverType    the type of cover
     * @param dimensions   the book's physical dimensions
     * @param summary      a brief summary of the book
     * @param pages        number of pages
     * @param publishDate  publication date
     * @param isbn         ISBN number
     */
    public BookDetailsEntity(BigDecimal weight, String coverType, String dimensions,
                             String summary, int pages, LocalDate publishDate, String isbn) {
        this.weight = weight;
        this.coverType = coverType;
        this.dimensions = dimensions;
        this.summary = summary;
        this.pages = pages;
        this.publishDate = publishDate;
        this.isbn = isbn;
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



    /**
     * The @ToString annotation excludes the 'book' field
     * to prevent circular references during logging or debugging.
     */
}
