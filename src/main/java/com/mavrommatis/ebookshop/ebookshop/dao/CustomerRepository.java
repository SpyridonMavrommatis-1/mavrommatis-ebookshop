package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Customer entities.
 * Provides access methods for user accounts, including email, username, etc.
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
