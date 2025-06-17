package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import org.mapstruct.*;

/**
 * MapStruct mapper interface for transforming {@link BookEntity} and related
 * objects to and from their corresponding DTO representations.
 *
 * <p>This mapper encapsulates the logic for converting between:
 * <ul>
 *   <li>{@link BookRequestDTO} → {@link BookEntity}</li>
 *   <li>{@link BookEntity} → {@link BookResponseDTO}</li>
 *   <li>{@link BookDetailsDTO} ↔ {@link BookDetailsEntity}</li>
 * </ul>
 *
 * <p>Some fields are handled specially:
 * <ul>
 *   <li>{@code author} is mapped from {@code authorId} using a stub {@link AuthorEntity}</li>
 *   <li>{@code bookDetails} is mapped with helper methods, or ignored when not applicable</li>
 * </ul>
 *
 * <p>This interface is a Spring-managed component and can be injected via
 * dependency injection where needed.</p>
 *
 * @see BookEntity
 * @see BookRequestDTO
 * @see BookResponseDTO
 * @see BookDetailsDTO
 * @see BookDetailsEntity
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
