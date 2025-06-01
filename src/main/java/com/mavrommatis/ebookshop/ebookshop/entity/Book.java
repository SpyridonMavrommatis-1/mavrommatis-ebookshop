package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="book")
public class Book {

    //define fields

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

    //ΝΟΤΕ: delete a book record means deleting automatically the bookDetail record (bookDetails is dependent).
    // but delete a bookDetails record means that the book record will not be deleted (book is independent as the owner).

}
