package com.mavrommatis.ebookshop.ebookshop.entity.basic;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mavrommatis.ebookshop.ebookshop.entity.helper.AuthorBookIdEntity;
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

    /**
     * A fixed version identifier for this class, required when implementing {@link Serializable}.
     *
     * <p>By explicitly declaring a {@code serialVersionUID}, we ensure that the JVM will not
     * auto-generate one every time the class is modified. This is particularly important in
     * production environments where the application is deployed without a full restart — such as in
     * hot-reload scenarios or rolling deployments — and the serialized form of the class may still
     * be in use (e.g., in caches, sessions, or queues).
     *
     * <p>If the class is changed and no fixed Unique Identifier is defined, Java computes a new one on-the-fly
     * based on the class's structure. This often causes deserialization to fail with
     * {@link java.io.InvalidClassException} if the runtime expects the old version of the class.
     *
     * <p>Setting a static {@code serialVersionUID = 1L} locks the class version and prevents
     * accidental incompatibility across versions during deployment or persistence cycles.
     */
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
