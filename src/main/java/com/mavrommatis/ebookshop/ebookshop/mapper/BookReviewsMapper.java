package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper interface for converting between {@link BookReviewsEntity}
 * and its corresponding request/response DTOs.
 *
 * <p>Handles one-way mapping from DTOs to entities and vice versa.
 * Used by the service layer to handle persistence-layer decoupling and to simplify
 * conversion logic between incoming HTTP requests and domain models.</p>
 *
 * <p>Note: The {@code customer} field is not mapped automatically and must be handled
 * manually in the service layer (e.g. by using the SecurityContext).</p>
 *
 * @see BookReviewsRequestDTO
 * @see BookReviewsResponseDTO
 * @see BookReviewsEntity
 */
@Mapper(componentModel = "spring")
public interface BookReviewsMapper {

    /**
     * Converts a {@link BookReviewsRequestDTO} to a {@link BookReviewsEntity} for persistence.
     * <p>
     * - The {@code book} field is set via a stub {@link BookEntity} created from bookId.
     * - The {@code customer} field is intentionally ignored and must be injected by the service.
     * - {@code reviewId}, {@code createdAt}, and {@code updatedAt} are ignored to allow
     *   database auto-generation or manual control.
     * </p>
     *
     * @param dto the incoming review request DTO
     * @return the mapped entity, with unmapped fields set to null
     */
    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBook")
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookReviewsEntity toEntity(BookReviewsRequestDTO dto);

    /**
     * Converts a {@link BookReviewsEntity} to a {@link BookReviewsResponseDTO} for response.
     * <p>
     * Extracts the foreign key IDs from nested {@code book} and {@code customer} entities.
     * </p>
     *
     * @param entity the persisted BookReviewsEntity
     * @return the response DTO for output
     */
    @Mapping(source = "book.bookId", target = "bookId")
    @Mapping(source = "customer.customerId", target = "customerId")
    BookReviewsResponseDTO toResponse(BookReviewsEntity entity);

    /**
     * Helper method to create a stub {@link BookEntity} based only on its ID.
     * <p>
     * This allows MapStruct to map an incoming {@code bookId} into a full BookEntity
     * reference that can be attached to the review.
     * </p>
     *
     * @param id the book ID to map
     * @return a minimal BookEntity with only ID set
     */
    @Named("mapBook")
    default BookEntity mapBook(Integer id) {
        if (id == null) return null;
        BookEntity b = new BookEntity();
        b.setBookId(id);
        return b;
    }
}
