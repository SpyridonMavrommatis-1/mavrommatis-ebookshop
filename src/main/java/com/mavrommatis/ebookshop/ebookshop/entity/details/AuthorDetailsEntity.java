package com.mavrommatis.ebookshop.ebookshop.entity.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents additional details for an Author entity.
 * This class is mapped in a one-to-one relationship with {@link AuthorEntity},
 * sharing the same primary key.
 */
@Entity
@Table(name = "author_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "author")
public class AuthorDetailsEntity {

    /** The ID of the author, shared with AuthorEntity. */
    @Id
    @Column(name = "author_id")
    private int authorId;

    /** A short or long biography for the author. */
    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    /** The author's date of birth. */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /** The personal or professional website of the author. */
    @Column(name = "website")
    private String website;

    /** Timestamp of when the record was created. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Timestamp of the last update to the record. */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * The associated {@link AuthorEntity} (parent).
     * Uses @MapsId to ensure shared primary key.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private AuthorEntity author;

    /**
     * Constructs AuthorDetailsEntity with key metadata.
     *
     * @param biography biography of the author
     * @param birthDate birth date
     * @param website   website link
     */
    public AuthorDetailsEntity(String biography, LocalDate birthDate, String website) {
        this.biography = biography;
        this.birthDate = birthDate;
        this.website = website;
    }

    /** Sets createdAt before persisting. */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /** Sets updatedAt before update. */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}