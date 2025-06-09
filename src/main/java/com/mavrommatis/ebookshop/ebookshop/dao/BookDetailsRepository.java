package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for BookDetails.
 * BookDetails shares its primary key with Book (one-to-one relationship).
 */
public interface BookDetailsRepository extends JpaRepository<BookDetailsEntity, Integer> {

}
