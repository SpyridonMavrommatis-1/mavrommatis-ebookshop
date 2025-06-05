package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;

import java.util.List;
import java.util.Optional;

public interface AuthorDetailsService {

    //Return all authordetails from the database
    List<AuthorDetails> findAll();

    //Return a record of authordetails by id
    Optional<AuthorDetails> findById(Integer id);

    //Save a record of authordetails to the database
    AuthorDetails save(AuthorDetails authorDetails);

    //Save multiple authordetails at once
    List<AuthorDetails> saveAll(List<AuthorDetails> authorDetails);

    //Delete a record of authordetails by ID if it exists
    void deleteById(Integer id);

    //Delete multiple authordetails by ID if they exists
    void deleteAllById(List<Integer> ids);
}

