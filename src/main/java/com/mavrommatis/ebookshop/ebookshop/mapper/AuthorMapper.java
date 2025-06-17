package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper interface responsible for converting between author-related
 * entities and their corresponding Data Transfer Objects (DTOs).
 *
 * <p>This mapper handles transformation logic between:
 * <ul>
 *   <li>{@link com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity AuthorEntity}</li>
 *   <li>{@link com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO AuthorRequestDTO}</li>
 *   <li>{@link com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO AuthorResponseDTO}</li>
 *   <li>{@link com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity AuthorDetailsEntity}</li>
 *   <li>{@link com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO AuthorDetailsDTO}</li>
 * </ul>
 *
 * <p>Nested mapping for {@code AuthorDetails} is supported automatically.</p>
 *
 * <p>Used by services to separate domain logic from representation and persistence layers.</p>
 *
 * @see com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity
 * @see com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO
 * @see com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity
 * @see com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {

    /**
     * Maps AuthorRequestDTO to AuthorEntity.
     * Nested details mapping is handled by MapStruct.
     */
    @Mapping(target = "authorDetails", source = "authorDetails")
    AuthorEntity toEntity(AuthorRequestDTO dto);

    /**
     * Maps AuthorEntity to AuthorResponseDTO for client.
     */
    @Mapping(target = "authorDetails", source = "authorDetails")
    AuthorResponseDTO toResponse(AuthorEntity entity);

    /**
     * Maps AuthorDetailsDTO to AuthorDetailsEntity.
     */
    AuthorDetailsEntity toEntity(AuthorDetailsDTO dto);

    /**
     * Maps AuthorDetailsEntity to AuthorDetailsDTO.
     */
    AuthorDetailsDTO toDto(AuthorDetailsEntity entity);
}