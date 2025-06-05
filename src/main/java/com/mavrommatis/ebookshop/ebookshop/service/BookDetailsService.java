package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;

import java.util.List;
import java.util.Optional;

public interface BookDetailsService {

    //Return all bookdetails from the database
    List<BookDetails> findAll();

    //Return a record of bookdetails by id
    Optional<BookDetails> findById(Integer id);

    //Save a record of bookdetails to the database
    BookDetails save(BookDetails bookDetails);

    //Save multiple bookdetails at once
    List<BookDetails> saveAll(List<BookDetails> bookDetails);

    //Delete a record of bookdetails by ID if it exists
    void deleteById(Integer id);

    //Delete multiple bookdetails by ID if they exists
    void deleteAllById(List<Integer> ids);
}


