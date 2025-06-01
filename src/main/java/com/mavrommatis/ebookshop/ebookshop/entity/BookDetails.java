package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="book_details")
public class BookDetails {

    //------------define fields------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="book_id")
    private int bookId;

    @Column (name="isbn")
    private String isbn;

    @Column (name="publish_date")
    private LocalDate publishDate;

    @Column (name="pages")
    private int pages;

    //columnDefinition = "TEXT" means that this is not a limited string
    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column (name="dimensions")
    private String dimensions;

    @Column (name="cover_type")
    private String coverType;

    @Column (name="weight")
    private BigDecimal weight;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // The @MapsId indicates that we use the same book_id for both Book and BookDetails (shared primary key).
    // @JoinColumn(name = "book_id") indicates that this field is also a foreign key to the book table.
    @OneToOne
    @MapsId
    @JoinColumn(name = "book_id")
    private Book book;

    //ΝΟΤΕ: delete a book record means deleting automatically the bookDetail record (bookDetails is dependent).
    // but delete a bookDetails record means that the book record will not be deleted (book is independent as the owner).
}
