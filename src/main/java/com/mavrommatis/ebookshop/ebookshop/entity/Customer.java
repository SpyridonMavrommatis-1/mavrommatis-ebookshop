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

    //=============GENERATE CONSTRUCTORS=================//

    public Customer() {

    }

    public Customer(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    //===========GENERATE GETTERS AND SETTERS=============//


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(CustomerDetails customerDetails) {
        this.customerDetails = customerDetails;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    protected void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //=============GENERATE ΤOSTRING===============//


    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
