package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"author", "bookDetails"})
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;
    /**
     * ΝΟΤΕ: We don't always need to load the Author entity at all when retrieving a Book.
     * Using LAZY prevents Hibernate from fetching the associated Author unless it's explicitly accessed.
     */

    //A book has a detail (BookDetails).
    // The relationship is not held here, but by the book field in the BookDetails class.
    // Whatever I do in book (save, update, delete), affects BookDetails.
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // => This is where the "parent" reference for serialization starts
    private BookDetails bookDetails;
    /**
     * ΝΟΤΕ: delete a book record means deleting automatically the bookDetail record
     * (bookDetails is dependent).
     * but delete a bookDetails record means that the book record will not be deleted
     * (book is independent as the owner).
     */


    //=============GENERATE CONSTRUCTORS=================//

    /**
     * ΝΟΤΕ: Default constructor is required by JPA.
     * and will be triggered by Lombok annotation @NoArgsConstructor.
     *
     */

    public Book(String title, String language, String genre, String literaryForm, Author author) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.literaryForm = literaryForm;
        author.addBook(this);
    }
    /**
     * Parameterized constructor for creating Book instances
     * without setting the ID or one-to-one bookDetails field.
     * These should be set separately after instantiation.
     */

    //===========INSTEAD OF GETTERS AND SETTERS=============//

    // See @Getter @Setter

    //Automatically sets the creation timestamp before the entity is persisted
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Automatically sets the update timestamp before the entity is updated
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Protected setters to allow JPA/Hibernate lifecycle events to modify timestamps,
    // while preventing direct external modification.
    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    //=============INSTEAD OF ΤOSTRING===============//


    /**
     * NOTE:
     * We exclude fields like 'author' and 'bookDetails' from the toString()
     * with @ToString(exclude = {"author", "bookDetails"}) to avoid potential issues
     * with infinite recursion caused by bidirectional
     * relationships (e.g., Author → Book → Author → Book...).
     *
     * Including such nested objects in toString() may lead to stack overflow errors
     * during logging or debugging when entities reference each other.
     */
}
