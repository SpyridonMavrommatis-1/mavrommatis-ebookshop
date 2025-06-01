package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name="customer")
public class Customer {

    //define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="customer")
    private int customer;
}
