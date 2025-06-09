package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.AuthorBookDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorBookEntity;
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
                .bookDetails(BookDetailsMapper.toBookDetailsDTO(entity.getBookDetails()))
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
        entity.setBookDetails(BookDetailsMapper.toBookDetailsEntity(dto.getBookDetails()));

        if (dto.getAuthorBooks() != null) {
            List<AuthorBookEntity> authorBookEntities = toAuthorBookEntityList(dto.getAuthorBooks());
            for (AuthorBookEntity abe : authorBookEntities) {
                entity.addAuthorBook(abe);
            }
        }

        return entity;
    }

    /**
     * Converts a list of AuthorBookEntity to a list of AuthorBookDTO.
     *
     * @param entities the list of AuthorBookEntity
     * @return the corresponding list of AuthorBookDTO
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
     * Converts a list of AuthorBookDTO to a list of AuthorBookEntity.
     *
     * @param dtos the list of AuthorBookDTO
     * @return the corresponding list of AuthorBookEntity
     */
    private static List<AuthorBookEntity> toAuthorBookEntityList(List<AuthorBookDTO> dtos) {
        if (dtos == null) return null;

        return dtos.stream()
                .map(dto -> {
                    AuthorBookEntity entity = new AuthorBookEntity();
                    entity.setCreatedAt(dto.getCreatedAt());
                    entity.setUpdatedAt(dto.getUpdatedAt());
                    // Author and Book references must be set in the service layer.
                    return entity;
                })
                .collect(Collectors.toList());
    }
}
