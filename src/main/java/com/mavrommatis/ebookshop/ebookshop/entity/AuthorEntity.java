package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
 * An author can have author details (1-1) and multiple books (1-many).
 */
@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"authorDetails"})
public class AuthorEntity {

    /**
     * The primary key of the author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorId;

    /**
     * The first name of the author.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the author.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The author's email address.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The timestamp when the author was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * The timestamp when the author was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One-to-one relation with AuthorDetailsEntity.
     */
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private AuthorDetailsEntity authorDetails;

    /**
     * One-to-many relation with BookEntity.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookEntity> books = new ArrayList<>();

    /**
     * Many-to-many relation with AuthorBookEntity.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuthorBookEntity> authorBooks = new ArrayList<>();

    /**
     * Constructor with first name and last name.
     */
    public AuthorEntity(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Sets the creation timestamp.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Sets the update timestamp.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Adds a book to the list and sets the back-reference.
     */
    public void addBook(BookEntity book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setAuthor(this);
        }
    }

    /**
     * Adds an AuthorBook entry to the list and sets the back-reference.
     */
    public void addAuthorBook(AuthorBookEntity authorBook) {
        if (!authorBooks.contains(authorBook)) {
            authorBooks.add(authorBook);
            authorBook.setAuthor(this);
        }
    }
}
