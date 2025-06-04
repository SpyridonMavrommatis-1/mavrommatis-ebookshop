package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    //Return all books from the database
    List<Book> findAll();

    //Return a book by id
    Optional<Book> findById(Integer id);

    //Save a single book to the database
    Book save(Book book);

    //Save multiple books at once
    public List<Book> saveAll(List<Book> books);

    //Delete an author by ID if it exists
    void deleteById(Integer id);

    //Delete multiple authors by ID if they exists
    public void deleteAllById(List<Integer> ids);
}

