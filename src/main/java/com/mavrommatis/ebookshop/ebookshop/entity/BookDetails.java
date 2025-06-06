package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * TODO Documentation
 */
@Entity
@Table(name = "book_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "book")
public class BookDetails {

    //============DEFINE FIELDS=============//

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
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "book_id")
    @JsonBackReference  // => Here the reference is "cut" to avoid a circular loop
    private Book book;

    //ΝΟΤΕ: delete a book record means deleting automatically the bookDetail record (bookDetails is dependent).
    // but delete a bookDetails record means that the book record will not be deleted (book is independent as the owner).


    //=============GENERATE CONSTRUCTORS=================//
    //See Book Notes

    public BookDetails(BigDecimal weight, String coverType, String dimensions,
                       String summary, int pages, LocalDate publishDate, String isbn) {
        this.weight = weight;
        this.coverType = coverType;
        this.dimensions = dimensions;
        this.summary = summary;
        this.pages = pages;
        this.publishDate = publishDate;
        this.isbn = isbn;
    }

    //===========INSTEAD OF GETTERS AND SETTERS=============//

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //=============INSTEAD OF ΤOSTRING===============//
    //See Book Notes

}
