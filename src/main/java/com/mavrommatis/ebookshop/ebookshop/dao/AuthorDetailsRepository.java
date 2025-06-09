package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing AuthorDetails entities.
 * Each AuthorDetails record is linked to an Author via a shared primary key.
 */
public interface AuthorDetailsRepository extends JpaRepository<AuthorDetailsEntity, Integer> {

}
