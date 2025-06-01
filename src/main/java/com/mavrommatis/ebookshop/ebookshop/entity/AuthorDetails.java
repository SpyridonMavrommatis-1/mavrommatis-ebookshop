package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="author_details")
public class AuthorDetails {

    //------------define fields------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="author_id")
    private int authorId;

    @Column (name="biography", columnDefinition = "TEXT")
    private String biography;

    @Column (name="birth_date")
    private LocalDate birthDate;

    @Column (name="website")
    private String website;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //See relevant book-book_details relation comments.
    @OneToOne
    @MapsId
    @JoinColumn(name = "author_id")
    private Author author;

}
