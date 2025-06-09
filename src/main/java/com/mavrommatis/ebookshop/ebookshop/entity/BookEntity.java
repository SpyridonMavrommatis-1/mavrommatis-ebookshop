package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
 * Represents a Book entity that belongs to an {@link AuthorEntity} and may contain additional
 * {@link BookDetailsEntity}. Supports automatic creation and update timestamps.
 */
@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"author", "bookDetails"})
public class BookEntity {

    /**
     * Unique identifier for the book (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int bookId;

    /**
     * Title of the book.
     */
    @Column(name = "title")
    private String title;

    /**
     * Language in which the book is written.
     */
    @Column(name = "language")
    private String language;

    /**
     * Genre or category of the book.
     */
    @Column(name = "genre")
    private String genre;

    /**
     * Indicates the literary form (e.g., Novel, Poem).
     */
    @Column(name = "literary_form")
    private String literaryForm;

    /**
     * Timestamp for when the book was created.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp for when the book was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Many-to-One relationship to the {@link AuthorEntity} entity.
     * This book belongs to a single author.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private AuthorEntity author;

    /**
     * Represents the one-to-one association with {@link BookDetailsEntity}, where this entity is the parent.
     * <p>
     * Cascade operations ensure that any changes to the {@link BookEntity} (such as persist, merge, or delete)
     * are propagated to the associated {@link BookDetailsEntity}. The {@code orphanRemoval=true} flag ensures
     * that removing the reference to {@link BookDetailsEntity} from this {@link BookEntity} will also delete the orphaned
     * {@link BookDetailsEntity} record from the database.
     * <p>
     * This relationship is bidirectional and mapped by the {@code book} field in the {@link BookDetailsEntity} entity.
     */
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private BookDetailsEntity bookDetails;

    /**
     * Bidirectional one-to-many relationship with AuthorBook.
     * Represents the intermediate association between Book and Author.
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuthorBookEntity> authorBooks = new ArrayList<>();

    /**
     * Constructs a new Book with the specified fields.
     * Automatically adds this book to the provided author's book list.
     *
     * @param title         the title of the book
     * @param language      the language of the book
     * @param genre         the genre of the book
     * @param literaryForm  the literary form
     * @param author        the author of the book
     */
    public BookEntity(String title, String language, String genre, String literaryForm, AuthorEntity author) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.literaryForm = literaryForm;
        author.addBook(this);
    }

    /**
     * Sets the creation timestamp before persisting.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Sets the update timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }


    /**
     * Adds an AuthorBook association to the book's list.
     * Ensures bidirectional consistency by setting this book
     * as the reference in the provided AuthorBook entity.
     *
     * @param authorBook the AuthorBook entity to associate with this book
     */
    public void addAuthorBook(AuthorBookEntity authorBook) {
        if (!authorBooks.contains(authorBook)) {
            authorBooks.add(authorBook);
            authorBook.setBook(this);
        }
    }

    /**
     * Note:
     * The {@code @ToString(exclude = {"author", "bookDetails"})} annotation
     * avoids recursive printing due to bidirectional relationships.
     */
}
