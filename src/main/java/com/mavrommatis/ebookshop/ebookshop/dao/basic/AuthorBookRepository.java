package com.mavrommatis.ebookshop.ebookshop.dao.basic;

import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.helper.AuthorBookIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AuthorBookEntity} persistence.
 * <p>
 * This repository handles CRUD operations for the many-to-many relationship
 * between Author and Book entities, using a composite primary key defined by {@link AuthorBookIdEntity}.
 * </p>
 *
 * Example usage:
 * <pre>
 *     AuthorBookIdEntity id = new AuthorBookIdEntity(authorId, bookId);
 *     Optional&lt;AuthorBookEntity&gt; relation = authorBookRepository.findById(id);
 * </pre>
 *
 * @see AuthorBookEntity
 * @see AuthorBookIdEntity
 */
@Repository
public interface AuthorBookRepository extends JpaRepository<AuthorBookEntity, AuthorBookIdEntity> {
}
