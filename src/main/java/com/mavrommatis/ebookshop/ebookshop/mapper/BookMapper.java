package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.*;
import com.mavrommatis.ebookshop.ebookshop.entity.*;
import org.mapstruct.*;

/**
 * Mapper for converting between Book entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

    /**
     * Maps a BookRequestDTO to a BookEntity for persistence.
     * <p>
     * - Stub AuthorEntity is created from authorId.
     * - BookDetailsEntity mapping is handled separately in service.
     * </p>
     * @param dto incoming request DTO
     * @return mapped BookEntity
     */
    @Mapping(target = "author", source = "authorId", qualifiedByName = "mapAuthor")
    @Mapping(target = "bookDetails", ignore = true)
    BookEntity toEntity(BookRequestDTO dto);

    /**
     * Updates an existing BookEntity in-place based on non-null fields from BookRequestDTO.
     * <p>
     * - Ignores nested author and details mappings; handle those in service.
     * - Null properties in DTO will be skipped (no overwrite).
     * </p>
     * @param dto    the source request DTO
     * @param entity the target managed BookEntity to update
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "bookDetails", ignore = true)
    void updateEntity(BookRequestDTO dto, @MappingTarget BookEntity entity);

    /**
     * Maps a BookEntity to a BookResponseDTO for client response.
     * <p>
     * - Author full name is concatenated.
     * - Nested BookDetailsDTO is mapped from BookDetailsEntity.
     * </p>
     * @param entity persisted BookEntity
     * @return outbound response DTO
     */
    @Mapping(target = "authorName", expression = "java(entity.getAuthor().getFirstName() + ' ' + entity.getAuthor().getLastName())")
    @Mapping(target = "details", source = "bookDetails")
    BookResponseDTO toResponse(BookEntity entity);

    /**
     * Maps nested BookDetailsDTO to BookDetailsEntity.
     */
    BookDetailsEntity bookDetailsDtoToEntity(BookDetailsDTO dto);

    /**
     * Maps nested BookDetailsEntity to BookDetailsDTO.
     */
    BookDetailsDTO bookDetailsEntityToDto(BookDetailsEntity entity);

    /**
     * Creates an AuthorEntity with only its ID set, for mapping.
     *
     * @param authorId the ID of the author
     * @return stub AuthorEntity
     */
    @Named("mapAuthor")
    default AuthorEntity mapAuthor(Integer authorId) {
        if (authorId == null) {
            return null;
        }
        AuthorEntity author = new AuthorEntity();
        author.setAuthorId(authorId);
        return author;
    }
}
