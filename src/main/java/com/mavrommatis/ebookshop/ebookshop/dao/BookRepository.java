package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Book entities.
 * Provides methods for saving, updating, deleting and retrieving books.
 */
public interface BookRepository extends JpaRepository<Book, Integer> {

}
