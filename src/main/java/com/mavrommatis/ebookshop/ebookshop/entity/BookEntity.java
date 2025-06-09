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
 * Represents a Book entity stored in the database.
 * Each book has a unique identifier and is associated with one main author.
 * Additional information is handled via related entities such as BookDetailsEntity and AuthorBookEntity.
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
     * Title of the book (cannot be null).
     */
    @Column(name = "title", nullable = false, length = 255)
    private String title;

    /**
     * Language in which the book is written (cannot be null).
     */
    @Column(name = "language", nullable = false, length = 100)
    private String language;

    /**
     * Genre or category of the book (e.g., fantasy, thriller) (cannot be null).
     */
    @Column(name = "genre", nullable = false, length = 100)
    private String genre;

    /**
     * Literary form of the book (e.g., novel, poem, essay) (cannot be null).
     */
    @Column(name = "literary_form", nullable = false, length = 100)
    private String literaryForm;

    /**
     * ISBN code of the book (unique per author/collective combination).
     */
    @Column(name = "isbn", nullable = false, length = 20)
    private String isbn;

    /**
     * Indicates whether the book is a collective work or not.
     */
    @Column(name = "is_collective", nullable = false)
    private boolean isCollective = false;

    /**
     * Timestamp when the book was created (automatically set on persist).
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the book was last updated (automatically updated on change).
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Many-to-One relationship to the main author of the book.
     * Every book must be associated with a single author.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private AuthorEntity author;

    /**
     * One-to-One relationship with the book's detailed metadata.
     * Cascades operations and removes orphaned details automatically.
     */
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private BookDetailsEntity bookDetails;

    /**
     * One-to-Many relationship with intermediate AuthorBook entities (for many-to-many logic).
     */
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<AuthorBookEntity> authorBooks = new ArrayList<>();

    /**
     * Constructs a new BookEntity and assigns it to the specified author.
     *
     * @param title         the book's title
     * @param language      the language in which the book is written
     * @param genre         the genre/category of the book
     * @param literaryForm  the literary form (e.g., novel, poem)
     * @param isbn          the ISBN of the book
     * @param isCollective  flag indicating whether this is a collective work
     * @param author        the author entity associated with the book
     */
    public BookEntity(String title, String language, String genre, String literaryForm, String isbn, boolean isCollective, AuthorEntity author) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.literaryForm = literaryForm;
        this.isbn = isbn;
        this.isCollective = isCollective;
        this.author = author;
        author.addBook(this);  // maintains bidirectional link
    }

    /**
     * Lifecycle callback to set the creation and update timestamps when the book is first persisted.
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Lifecycle callback to update the timestamp when the book is modified.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adds an AuthorBook association to this book's list and sets the reverse relationship.
     *
     * @param authorBook the AuthorBook entity to associate
     */
    public void addAuthorBook(AuthorBookEntity authorBook) {
        if (!authorBooks.contains(authorBook)) {
            authorBooks.add(authorBook);
            authorBook.setBook(this);
        }
    }
}
