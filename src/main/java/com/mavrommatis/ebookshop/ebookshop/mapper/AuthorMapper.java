package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Author entities and DTOs.
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