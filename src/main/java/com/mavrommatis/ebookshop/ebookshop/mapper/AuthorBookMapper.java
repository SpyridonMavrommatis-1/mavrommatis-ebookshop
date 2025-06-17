package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper interface for converting between
 * {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity AuthorBookEntity}
 * and its corresponding request/response DTOs.
 *
 * <p>This mapper handles the transformation logic for the many-to-many relationship
 * between {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity AuthorEntity}
 * and {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity BookEntity},
 * represented in the join table entity {@code AuthorBookEntity}.</p>
 *
 * <p>During mapping from request to entity:
 * <ul>
 *   <li>Only the IDs of related entities are used (stub Author and Book objects are created).</li>
 *   <li>Timestamps are ignored, as they're managed by JPA lifecycle hooks.</li>
 * </ul>
 *
 * <p>During mapping from entity to response:
 * <ul>
 *   <li>All relevant fields (IDs, timestamps) are extracted for outbound DTOs.</li>
 * </ul>
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorBookRequestDTO
 * @see com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity
 */
@Mapper(componentModel = "spring")
public interface AuthorBookMapper {

    /**
     * Maps AuthorBookRequestDTO to AuthorBookEntity.
     * <p>
     * - Stubs AuthorEntity and BookEntity from IDs.
     * - Ignores timestamps on creation.
     * </p>
     */
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBook")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    AuthorBookEntity toEntity(AuthorBookRequestDTO dto);

    /**
     * Maps AuthorBookEntity to AuthorBookResponseDTO.
     */
    AuthorBookResponseDTO toResponse(AuthorBookEntity entity);

    /**
     * Creates a stub AuthorEntity based on its ID.
     */
    @Named("mapAuthor")
    default AuthorEntity mapAuthor(Integer id) {
        if (id == null) return null;
        AuthorEntity a = new AuthorEntity(); a.setAuthorId(id); return a;
    }

    /**
     * Creates a stub BookEntity based on its ID.
     */
    @Named("mapBook")
    default BookEntity mapBook(Integer id) {
        if (id == null) return null;
        BookEntity b = new BookEntity(); b.setBookId(id); return b;
    }
}
