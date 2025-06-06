package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on Author entities.
 * Extends JpaRepository to provide standard database operations.
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
