package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="author")
public class Author {

    //------------define fields------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="author_id")
    private int authorId;

    @Column (name="first_name")
    private String firstName;

    @Column (name="last_name")
    private String lastName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //See relevant book-book_details relation comments.
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL)
    private AuthorDetails authorDetails;
}
