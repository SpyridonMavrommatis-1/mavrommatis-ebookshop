package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
