package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"authorDetails"})
public class Author {

    //============DEFINE FIELDS=============//

    private static final Logger logger = LoggerFactory.getLogger(Author.class);

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
    @JsonManagedReference
    private AuthorDetails authorDetails;


    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();
    /**
     * !!!Note: Here, within the existing OneToOne relations, we are now adding a OneToMany bidirectional relation.
     * This means that:
     * - One Author can have many Books (List<Book>).
     * - Each Book will reference back to its Author via a @ManyToOne field.
     * - The relation is bidirectional: both Author and Book are aware of each other.
     */


    //=============GENERATE CONSTRUCTORS=================//
    //See Book Notes


    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        logger.info("New Author created: {}, {}", firstName, lastName);
    }

    //===========INSTEAD OF GETTERS AND SETTERS=============//
    //See Book Notes


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        logger.info("Author persisted with name: {}, createdAt: {}", firstName, createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        logger.info("Author updated with name: {}, updatedAt: {}", firstName, createdAt);
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setAuthor(this);
        }
    }
    /**
     * !!!Note: Instead of providing a public setter for the bidirectional OneToMany relation,
     * we define an `addBook` method that:
     * - Adds the Book to the Author's list only if it doesn't already exist (avoids duplicates).
     * - Sets the back-reference (`book.setAuthor(this)`) to maintain consistency in the bidirectional mapping.
     * This ensures referential integrity and encapsulates the relationship logic in one place.
     */


    //=============INSTEAD OF ΤOSTRING===============//
    //See Book Notes

}
