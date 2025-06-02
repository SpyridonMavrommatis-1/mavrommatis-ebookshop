package com.mavrommatis.ebookshop.ebookshop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


@Entity
@Table(name = "customer_details")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "customer")
public class CustomerDetails {

    //============DEFINE FIELDS=============//

    private static final Logger logger = LoggerFactory.getLogger(CustomerDetails.class);

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
        logger.info("New CustomerDetails created: {} {}", firstName,lastName);
    }


    //===========INSTEAD OF GETTERS AND SETTERS=============//

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        logger.info("CustomerDetails persisted with : {}, createdAt: {}", firstName, createdAt);
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        logger.info("CustomerDetails updated for firstName: {}, updatedAt: {}", firstName, updatedAt);
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
