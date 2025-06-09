package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for CustomerDetails entities.
 * Contains personal information mapped one-to-one with Customer.
 */
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Integer> {
    
}
