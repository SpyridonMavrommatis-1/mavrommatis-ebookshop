package com.mavrommatis.ebookshop.ebookshop.entity.basic;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Represents a Customer in the ebook shop system.
 * <p>
 * Each customer has login credentials and can be linked to detailed personal information
 * via {@link CustomerDetailsEntity}.
 * <p>
 * This entity handles authentication and basic user identification.
 */
@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"customerDetails"})
public class CustomerEntity {

    /** Primary key for Customer entity. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    /** Unique username for the customer. */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Encrypted password of the customer.
     * This field should always be stored in hashed form (e.g., BCrypt).
     */
    @Column(name = "password", nullable = false)
    private String password;

    /** Email address of the customer (must be unique). */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** Timestamp when the customer is first registered. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Timestamp when the customer record was last updated. */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * One-to-one relationship with the customer's extended profile.
     * Uses lazy loading and manages the reverse side of the relationship.
     */
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private CustomerDetailsEntity customerDetails;

    /**
     * Constructs a Customer with basic login information.
     *
     * @param email    the customer's email
     * @param password the customer's encrypted password
     * @param username the customer's unique username
     */
    public CustomerEntity(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    /** Automatically sets the creation timestamp before the entity is persisted. */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    /** Automatically sets the update timestamp before the entity is updated. */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Protected setter for createdAt to prevent external override.
     *
     * @param createdAt timestamp to set
     */
    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Protected setter for updatedAt to prevent external override.
     *
     * @param updatedAt timestamp to set
     */
    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}