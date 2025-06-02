package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="book")
public class Book {

    //============DEFINE FIELDS=============//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="book_id")
    private int bookId;

    @Column (name="title")
    private String title;

    @Column (name="language")
    private String language;

    @Column (name="genre")
    private String genre;

    @Column (name="literary_form")
    private String literaryForm;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Many-to-One with Author
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    //A book has a detail (BookDetails).
    // The relationship is not held here, but by the book field in the BookDetails class.
    // Whatever I do in book (save, update, delete), affects BookDetails.
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetails bookDetails;
    /**
     * ΝΟΤΕ: delete a book record means deleting automatically the bookDetail record
     * (bookDetails is dependent).
     * but delete a bookDetails record means that the book record will not be deleted
     * (book is independent as the owner).
     */


    //=============GENERATE CONSTRUCTORS=================//


    public Book(){

    }
    /**
     * ΝΟΤΕ: Default constructor is required by JPA.
     * It allows Hibernate to instantiate the object using reflection.
     *
     */

    public Book(String title, String language, String genre, String literaryForm, LocalDateTime createdAt,
                LocalDateTime updatedAt, Author author) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.literaryForm = literaryForm;
        this.author = author;
    }
    /**
     * Parameterized constructor for creating Book instances
     * without setting the ID or one-to-one bookDetails field.
     * These should be set separately after instantiation.
     */


    //===========GENERATE GETTERS AND SETTERS=============//

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLiteraryForm() {
        return literaryForm;
    }

    public void setLiteraryForm(String literaryForm) {
        this.literaryForm = literaryForm;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    /** NOTE:
     * Audit fields (createdAt, updatedAt) using JPA lifecycle callbacks.
     *
     * - @PrePersist: Called automatically before the entity is inserted into the database.
     *   Sets createdAt to the current timestamp.
     *
     * - @PreUpdate: Called automatically before the entity is updated.
     *   Sets updatedAt to the current timestamp.
     *
     * - Getters allow external reading of these fields.
     * - Setters are marked as protected to prevent external modification,
     *   ensuring timestamps are only set via lifecycle methods.
     */

    //=============GENERATE ΤOSTRING===============//


    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", language='" + language + '\'' +
                ", genre='" + genre + '\'' +
                ", literaryForm='" + literaryForm + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
    /**
     * NOTE:
     * We exclude fields like 'author' and 'bookDetails' from the toString()
     * to avoid potential issues with infinite recursion caused by bidirectional
     * relationships (e.g., Author → Book → Author → Book...).
     *
     * Including such nested objects in toString() may lead to stack overflow errors
     * during logging or debugging when entities reference each other.
     */
}
