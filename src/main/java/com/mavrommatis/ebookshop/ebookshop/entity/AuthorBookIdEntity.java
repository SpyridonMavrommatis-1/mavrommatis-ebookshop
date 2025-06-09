package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the composite primary key for the {@link AuthorBookEntity} entity.
 * This class is used by JPA to uniquely identify each row in the 'author_book' table,
 * combining the {@code author_id} and {@code book_id} fields.
 * <p>
 * Must override {@code equals} and {@code hashCode} to function properly in JPA.
 */
@Embeddable
public class AuthorBookIdEntity implements Serializable {

    /**
     * The ID of the associated author.
     */
    @Column(name = "author_id")
    private int authorId;

    /**
     * The ID of the associated book.
     */
    @Column(name = "book_id")
    private int bookId;

    /**
     * Default constructor required by JPA.
     * Used during the deserialization or instantiation of embedded keys.
     */
    public AuthorBookIdEntity() {}

    /**
     * Constructs a composite key with the specified author and book IDs.
     *
     * @param authorId the ID of the author
     * @param bookId   the ID of the book
     */
    public AuthorBookIdEntity(int authorId, int bookId) {
        this.authorId = authorId;
        this.bookId = bookId;
    }

    /**
     * Gets the ID of the author in this composite key.
     *
     * @return the author's ID
     */
    public int getAuthorId() {
        return authorId;
    }

    /**
     * Sets the ID of the author in this composite key.
     *
     * @param authorId the new author's ID
     */
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    /**
     * Gets the ID of the book in this composite key.
     *
     * @return the book's ID
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the ID of the book in this composite key.
     *
     * @param bookId the new book's ID
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    /**
     * Determines if two composite keys are equal.
     * Equality is based on both {@code authorId} and {@code bookId}.
     *
     * @param o the object to compare
     * @return true if both keys match on authorId and bookId
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorBookIdEntity that)) return false;
        return authorId == that.authorId && bookId == that.bookId;
    }

    /**
     * Returns a hash code based on both {@code authorId} and {@code bookId}.
     * Required for JPA entity key handling.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(authorId, bookId);
    }
}
