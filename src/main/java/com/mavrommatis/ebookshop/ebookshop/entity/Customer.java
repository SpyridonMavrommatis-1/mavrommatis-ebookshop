package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="customer")
public class Customer {

    //------------define fields------------

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
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private CustomerDetails customerDetails;

}
