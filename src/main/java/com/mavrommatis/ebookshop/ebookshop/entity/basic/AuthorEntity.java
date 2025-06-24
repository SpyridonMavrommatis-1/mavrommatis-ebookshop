package com.mavrommatis.ebookshop.ebookshop.entity.basic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Author entity in the ebookshop system.
 * An author may have detailed metadata, one or more books,
 * and may also participate in collaborations via the AuthorBook join table.
 *
 * <p>Each author:
 * <ul>
 *     <li>Has detailed personal info via {@link AuthorDetailsEntity} (one-to-one)</li>
 *     <li>Is associated with one or more {@link BookEntity} (one-to-many)</li>
 *     <li>Participates in many-to-many relationships via {@link AuthorBookEntity}</li>
 * </ul>
 */
@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"authorDetails"})
public class AuthorEntity {

    /** Primary key for the author entity. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorId;

    /** First name of the author (required). */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /** Last name of the author (required). */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /** Email address of the author (must be unique). */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** Timestamp when the author was created (set automatically). */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Timestamp when the author was last updated (set automatically). */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** One-to-one relationship with author’s personal metadata. */
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private AuthorDetailsEntity authorDetails;

    /** One-to-many relationship with authored books. */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookEntity> books = new ArrayList<>();

    /** Many-to-many participation via AuthorBook relationship. */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuthorBookEntity> authorBooks = new ArrayList<>();

    /**
     * Constructs a new AuthorEntity with basic identification info.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param email     the unique email address
     */
    public AuthorEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /** Sets the creation timestamp before the entity is persisted. */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /** Updates the modification timestamp before updating the entity. */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Adds a book to the author's collection and sets the reverse relationship.
     *
     * @param book the BookEntity to add
     */
    public void addBook(BookEntity book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setAuthor(this); // maintain bidirectional consistency
        }
    }

    /**
     * Adds an AuthorBook entry to represent a many-to-many participation.
     *
     * @param authorBook the AuthorBookEntity to add
     */
    public void addAuthorBook(AuthorBookEntity authorBook) {
        if (!authorBooks.contains(authorBook)) {
            authorBooks.add(authorBook);
            authorBook.setAuthor(this); // maintain bidirectional consistency
        }
    }
}
