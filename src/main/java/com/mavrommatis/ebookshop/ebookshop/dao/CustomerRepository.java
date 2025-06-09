package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Customer entities.
 * Provides access methods for user accounts, including email, username, etc.
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

}
