package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Represents a Customer in the ebook shop system.
 * <p>
 * Each customer has login credentials and can be linked to detailed personal information.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"customerDetails"})
public class Customer {

    /**
     * Primary key for Customer entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    /**
     * Unique username for the customer.
     */
    @Column(name = "username")
    private String username;

    /**
     * Encrypted password of the customer.
     */
    @Column(name = "password")
    private String password;

    /**
     * Email address of the customer.
     */
    @Column(name = "email")
    private String email;

    /**
     * Timestamp when the customer is first registered.
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when the customer record was last updated.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One-to-one relationship with CustomerDetails.
     * Uses lazy loading and manages the reverse side of the relationship.
     */
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CustomerDetails customerDetails;

    /**
     * Constructs a Customer with basic login information.
     *
     * @param email    the customer's email
     * @param password the customer's password
     * @param username the customer's username
     */
    public Customer(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    /**
     * Automatically sets the creation timestamp before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /**
     * Automatically sets the update timestamp before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Protected setter for createdAt to prevent manual override.
     */
    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Protected setter for updatedAt to prevent manual override.
     */
    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * The @ToString annotation excludes the 'customerDetails' field
     * to prevent circular references during logging or debugging.
     */
}
