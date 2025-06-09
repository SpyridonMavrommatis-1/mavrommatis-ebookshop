package com.mavrommatis.ebookshop.ebookshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing detailed attributes of a book.
 * <p>
 * This includes metadata and physical attributes that are stored
 * separately from the main BookDTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailsDTO {

    /**
     * The ID of the book this detail belongs to.
     */
    private int bookId;

    /**
     * The International Standard Book Number.
     */
    private String isbn;

    /**
     * The date the book was published.
     */
    private LocalDate publishDate;

    /**
     * Total number of pages in the book.
     */
    private int pages;

    /**
     * A short summary or description of the book.
     */
    private String summary;

    /**
     * Physical dimensions of the book (e.g., 21x29 cm).
     */
    private String dimensions;

    /**
     * The type of book cover (e.g., Hardcover, Paperback).
     */
    private String coverType;

    /**
     * The weight of the book.
     */
    private BigDecimal weight;

    /**
     * Timestamp of when the record was created.
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp of the last update to the record.
     */
    private LocalDateTime updatedAt;
}
