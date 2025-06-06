package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents additional details for an {@link Author}, such as biography, birth date, and website.
 * This entity has a one-to-one relationship with the Author entity.
 */
@Entity
@Table(name = "author_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "author")
public class AuthorDetails {

    /**
     * The ID of the author. Shared primary key with the Author entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorId;

    /**
     * The biography of the author.
     */
    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    /**
     * The birth date of the author.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * The website URL of the author.
     */
    @Column(name = "website")
    private String website;

    /**
     * Timestamp indicating when the entity was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the entity was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Reference to the associated Author entity.
     * This is a bidirectional one-to-one relationship.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    /**
     * Constructs an AuthorDetails object with the given values.
     *
     * @param biography the biography text
     * @param birthDate the date of birth
     * @param website   the website URL
     */
    public AuthorDetails(String biography, LocalDate birthDate, String website) {
        this.biography = biography;
        this.birthDate = birthDate;
        this.website = website;
    }

    /**
     * Sets the creation timestamp before persisting the entity.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Sets the update timestamp before updating the entity.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Protected setter for createdAt to avoid unintended modification.
     *
     * @param createdAt the creation timestamp
     */
    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Protected setter for updatedAt to avoid unintended modification.
     *
     * @param updatedAt the update timestamp
     */
    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}