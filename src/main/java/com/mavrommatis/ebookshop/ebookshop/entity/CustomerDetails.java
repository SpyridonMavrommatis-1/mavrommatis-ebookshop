package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Entity
@Table(name = "customer_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "customer")
public class CustomerDetails {

    //============DEFINE FIELDS=============//

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
    @JsonBackReference
    private Customer customer;

    //=============GENERATE CONSTRUCTORS=================//
    //See Book Notes

    public CustomerDetails(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
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
