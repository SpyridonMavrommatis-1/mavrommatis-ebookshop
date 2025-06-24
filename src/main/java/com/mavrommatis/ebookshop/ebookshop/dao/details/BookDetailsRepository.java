package com.mavrommatis.ebookshop.ebookshop.dao.details;

import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for BookDetails.
 * BookDetails shares its primary key with Book (one-to-one relationship).
 */
public interface BookDetailsRepository extends JpaRepository<BookDetailsEntity, Integer> {

}
