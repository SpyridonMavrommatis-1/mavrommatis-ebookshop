package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorBookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorBookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between AuthorBookEntity and AuthorBookResponseDTO.
 *
 * <p>
 * Only used for read operations in the system.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface AuthorBookMapper {

    /**
     * Converts an AuthorBookEntity to AuthorBookResponseDTO.
     * Extracts authorId and bookId from nested entities.
     */
    @Mapping(target = "authorId", source = "author.authorId")
    @Mapping(target = "bookId", source = "book.bookId")
    AuthorBookResponseDTO toResponse(AuthorBookEntity entity);

}
