package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;



@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"customerDetails"})
public class Customer {

    //============DEFINE FIELDS=============//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="customer_id")
    private int customerId;

    @Column (name="username")
    private String username;

    @Column (name="password")
    private String password;

    @Column (name="email")
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //See relevant book-book_details relation comments.
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CustomerDetails customerDetails;


    //=============GENERATE CONSTRUCTORS=================//
    //See Book Notes
    public Customer(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
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
