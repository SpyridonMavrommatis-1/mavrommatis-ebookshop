package com.mavrommatis.ebookshop.ebookshop.entity.details;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Represents detailed personal information of a Customer.
 * <p>
 * This entity is in a one-to-one relationship with the {@link CustomerEntity},
 * sharing the same primary key.
 */
@Entity
@Table(name = "customer_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "customer")
public class CustomerDetailsEntity {

    /** Primary key and foreign key. Shared with the Customer entity. */
    @Id
    @Column(name = "customer_id")
    private int customerId;

    /** The customer's first name. */
    @Column(name = "first_name")
    private String firstName;

    /** The customer's last name. */
    @Column(name = "last_name")
    private String lastName;

    /** The customer's street address. */
    @Column(name = "address")
    private String address;

    /** The customer's contact phone number. */
    @Column(name = "phone")
    private String phone;

    /** Timestamp when the customer details record is created. */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** Timestamp when the customer details record was last updated. */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Back-reference to the main {@link CustomerEntity}.
     * Uses @MapsId to share primary key and @JsonBackReference to prevent recursion in serialization.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private CustomerEntity customer;

    /**
     * Constructs a CustomerDetails object with basic personal data.
     */
    public CustomerDetailsEntity(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
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

    /** Protected setter for createdAt to prevent manual override. */
    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /** Protected setter for updatedAt to prevent manual override. */
    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** @ToString excludes the 'customer' field to avoid circular references in logs. */
}
