package com.mavrommatis.ebookshop.ebookshop.entity;

import jakarta.persistence.*;

@Entity
@Table(name="author_details")
public class AuthorDetails {

    //define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="author_id")
    private int authorId;
}
