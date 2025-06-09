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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One-to-one relation with AuthorDetails.
     */
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private AuthorDetailsEntity authorDetails;

    /**
     * One-to-many relation with Book.
     * One author can have multiple books.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookEntity> books = new ArrayList<>();

    /**
     * Bidirectional one-to-many relationship with AuthorBook.
     * Represents the intermediate association between Author and Book.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuthorBookEntity> authorBooks = new ArrayList<>();


    /**
     * Constructor used to create an Author with first and last name.
     *
     * @param firstName the author's first name
     * @param lastName  the author's last name
     */
    public AuthorEntity(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
     * Adds a book to the author and sets the author reference in the book.
     *
     * @param book the book to be added
     */
    public void addBook(BookEntity book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setAuthor(this);
        }
    }
    /**
     * Adds an AuthorBook association to the author's list.
     * Ensures bidirectional consistency by setting this author
     * as the reference in the provided AuthorBook entity.
     *
     * @param authorBook the AuthorBook entity to associate with this author
     */
    public void addAuthorBook(AuthorBookEntity authorBook) {
        if (!authorBooks.contains(authorBook)) {
            authorBooks.add(authorBook);
            authorBook.setAuthor(this);
        }
    }

}