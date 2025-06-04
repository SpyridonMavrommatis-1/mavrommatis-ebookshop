package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    //See BookService notes

    List<Author> findAll();

    Optional<Author> findById(Integer id);

    Author save(Author author);

    public List<Author> saveAll(List<Author> authors);

    void deleteById(Integer id);

    public void deleteAllById(List<Integer> ids);
}
