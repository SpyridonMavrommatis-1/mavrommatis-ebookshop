package com.mavrommatis.ebookshop.ebookshop.dao;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for performing CRUD operations on Author entities.
 * Extends JpaRepository to provide standard database operations.
 */
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    /**
     * Determines whether an author exists in the database with the specified email address,
     * ignoring case sensitivity.
     *
     * <p>This method is typically used in validation scenarios (e.g., during registration or update)
     * to prevent duplicate email addresses from being assigned to multiple author records.</p>
     *
     * @param email the email address to check
     * @return {@code true} if an author with the given email exists; {@code false} otherwise
     */
    boolean existsByEmailIgnoreCase(String email);

}
