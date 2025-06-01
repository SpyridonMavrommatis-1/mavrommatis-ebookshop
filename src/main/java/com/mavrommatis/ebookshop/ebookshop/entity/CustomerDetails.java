package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="customer_details")
public class CustomerDetails {

    //------------define fields------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="customer_id")
    private int customerId;

    @Column (name="first_name")
    private String firstName;

    @Column (name="last_name")
    private String lastName;

    @Column (name="address")
    private String address;

    @Column (name="phone")
    private String phone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //See relevant book-book_details relation comments.
    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
