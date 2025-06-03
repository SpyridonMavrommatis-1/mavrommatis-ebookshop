package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "author_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "author")
public class AuthorDetails {

    //============DEFINE FIELDS=============//

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
    @JsonBackReference
    private Author author;

    //=============GENERATE CONSTRUCTORS=================//
    //See Book Notes

    public AuthorDetails(String biography, LocalDate birthDate, String website) {
        this.biography = biography;
        this.birthDate = birthDate;
        this.website = website;
    }

    //===========INSTEAD OF GETTERS AND SETTERS=============//
    //See Book Notes
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
