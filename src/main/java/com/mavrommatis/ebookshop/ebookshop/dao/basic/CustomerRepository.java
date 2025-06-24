package com.mavrommatis.ebookshop.ebookshop.dao.basic;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for Customer entities.
 * <p>
 * Provides CRUD operations and custom query methods for managing customer data.
 * This interface is automatically implemented by Spring Data JPA at runtime.
 * </p>
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    /**
     * Retrieves a customer by their unique username.
     * <p>
     * Spring Data JPA generates the query implementation automatically based on
     * the method name. Internally, this translates to:
     * <pre>
     * SELECT * FROM customer WHERE username = ?
     * </pre>
     * </p>
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the matching {@link CustomerEntity}, or empty if none found
     */
    Optional<CustomerEntity> findByUsername(String username);
}
