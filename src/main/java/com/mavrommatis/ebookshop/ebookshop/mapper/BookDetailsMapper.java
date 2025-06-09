package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;

public class BookDetailsMapper {

    /**
     * Converts a BookDetailsEntity to a BookDetailsDTO.
     *
     * @param entity the BookDetailsEntity to convert
     * @return the corresponding BookDetailsDTO
     */
    public static BookDetailsDTO toBookDetailsDTO(BookDetailsEntity entity) {
        if (entity == null) return null;

        return BookDetailsDTO.builder()
                .bookId(entity.getBookId())
                .isbn(entity.getIsbn())
                .publishDate(entity.getPublishDate())
                .pages(entity.getPages())
                .summary(entity.getSummary())
                .dimensions(entity.getDimensions())
                .coverType(entity.getCoverType())
                .weight(entity.getWeight())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    /**
     * Converts a BookDetailsDTO to a BookDetailsEntity.
     *
     * @param dto the BookDetailsDTO to convert
     * @return the corresponding BookDetailsEntity
     */
    public static BookDetailsEntity toBookDetailsEntity(BookDetailsDTO dto) {
        if (dto == null) return null;

        BookDetailsEntity entity = new BookDetailsEntity();
        entity.setBookId(dto.getBookId());
        entity.setIsbn(dto.getIsbn());
        entity.setPublishDate(dto.getPublishDate());
        entity.setPages(dto.getPages());
        entity.setSummary(dto.getSummary());
        entity.setDimensions(dto.getDimensions());
        entity.setCoverType(dto.getCoverType());
        entity.setWeight(dto.getWeight());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
