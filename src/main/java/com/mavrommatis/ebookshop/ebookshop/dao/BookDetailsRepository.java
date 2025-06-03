package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailsRepository extends JpaRepository<BookDetails, Integer> {

}
