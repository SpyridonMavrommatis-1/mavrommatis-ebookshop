package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between BookEntity and BookDTO.
 */
public class BookMapper {

    /**
     * Converts a BookEntity to a BookDTO.
     *
     * @param entity the BookEntity to convert
     * @return the corresponding BookDTO
     */
    public static BookDTO toBookDTO(BookEntity entity) {
        if (entity == null) return null;

        return BookDTO.builder()
                .bookId(entity.getBookId())
                .title(entity.getTitle())
                .language(entity.getLanguage())
                .genre(entity.getGenre())
                .literaryForm(entity.getLiteraryForm())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .authorId(entity.getAuthor() != null ? entity.getAuthor().getAuthorId() : null)
                .bookDetails(toBookDetailsDTO(entity.getBookDetails()))
                .authorBooks(toAuthorBookDTOList(entity.getAuthorBooks()))
                .build();
    }

    /**
     * Converts a BookDTO to a BookEntity.
     *
     * @param dto the BookDTO to convert
     * @return the corresponding BookEntity
     */
    public static BookEntity toBookEntity(BookDTO dto) {
        if (dto == null) return null;

        BookEntity entity = new BookEntity();
        entity.setBookId(dto.getBookId());
        entity.setTitle(dto.getTitle());
        entity.setLanguage(dto.getLanguage());
        entity.setGenre(dto.getGenre());
        entity.setLiteraryForm(dto.getLiteraryForm());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setBookDetails(toBookDetailsEntity(dto.getBookDetails()));

        if (dto.getAuthorBooks() != null) {
            List<AuthorBookEntity> authorBookEntities = toAuthorBookEntityList(dto.getAuthorBooks());
            for (AuthorBookEntity abe : authorBookEntities) {
                entity.addAuthorBook(abe);
            }
        }

        return entity;
    }

    /**
     * Converts a BookDetailsEntity to a BookDetailsDTO.
     *
     * @param entity the BookDetailsEntity to convert
     * @return the corresponding BookDetailsDTO
     */
    private static BookDetailsDTO toBookDetailsDTO(BookDetailsEntity entity) {
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
    private static BookDetailsEntity toBookDetailsEntity(BookDetailsDTO dto) {
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
    /**
     * Converts a list of {@link AuthorBookEntity} to a list of {@link AuthorBookDTO}.
     *
     * @param entities the list of AuthorBookEntity to convert
     * @return list of AuthorBookDTOs, or null if input is null
     */
    private static List<AuthorBookDTO> toAuthorBookDTOList(List<AuthorBookEntity> entities) {
        if (entities == null) return null;

        return entities.stream()
                .map(entity -> AuthorBookDTO.builder()
                        .authorId(entity.getAuthor() != null ? entity.getAuthor().getAuthorId() : null)
                        .bookId(entity.getBook() != null ? entity.getBook().getBookId() : null)
                        .createdAt(entity.getCreatedAt())
                        .updatedAt(entity.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of {@link AuthorBookDTO} to a list of {@link AuthorBookEntity}.
     *
     * @param dtos the list of AuthorBookDTOs to convert
     * @return list of AuthorBookEntities, or null if input is null
     */
    private static List<AuthorBookEntity> toAuthorBookEntityList(List<AuthorBookDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> {
                    AuthorBookEntity entity = new AuthorBookEntity();
                    entity.setCreatedAt(dto.getCreatedAt());
                    entity.setUpdatedAt(dto.getUpdatedAt());
                    // Note: Author and Book references must be set externally from services/repositories
                    return entity;
                })
                .collect(Collectors.toList());
    }
}
