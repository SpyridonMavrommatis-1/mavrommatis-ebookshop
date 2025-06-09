package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the many-to-many relationship between {@link AuthorEntity} and {@link BookEntity}.
 * This entity uses a composite primary key defined by {@link AuthorBookIdEntity}, and stores
 * additional metadata such as timestamps.
 * <p>
 * This relationship allows a book to have multiple authors and an author to have contributed
 * to multiple books.
 */
@Entity
@Table(name = "author_book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"author", "book"})
public class AuthorBookEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Composite primary key consisting of author_id and book_id.
     */
    @EmbeddedId
    private AuthorBookIdEntity id = new AuthorBookIdEntity();

    /**
     * Reference to the associated AuthorEntity.
     * Part of the composite key.
     * Prevents circular reference during JSON serialization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private AuthorEntity author;

    /**
     * Reference to the associated BookEntity.
     * Part of the composite key.
     * Prevents circular reference during JSON serialization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    @JsonBackReference
    private BookEntity book;

    /**
     * Timestamp indicating when the relationship was first created.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating the last update of the relationship.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Automatically sets the creation timestamp before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Automatically sets the update timestamp before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Custom constructor that also sets the composite key.
     *
     * @param author the AuthorEntity
     * @param book   the BookEntity
     */
    public AuthorBookEntity(AuthorEntity author, BookEntity book) {
        this.author = author;
        this.book = book;
        this.id = new AuthorBookIdEntity(author.getAuthorId(), book.getBookId());
    }

    /**
     * Equality is based solely on the composite key.
     *
     * @param o the object to compare
     * @return true if the IDs are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorBookEntity that)) return false;
        return Objects.equals(id, that.id);
    }

    /**
     * Hash code based on the composite key.
     *
     * @return hash code of the ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
