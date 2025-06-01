package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customer_details")
public class CustomerDetails {

    //define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="customer_details")
    private int customer_details;

}
