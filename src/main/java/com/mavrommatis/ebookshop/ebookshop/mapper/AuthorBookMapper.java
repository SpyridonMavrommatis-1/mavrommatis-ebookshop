package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting between AuthorBook entities and DTOs.
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
