package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
