package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.BookReviewResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.BookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for converting between BookReviews entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface BookReviewsMapper {

    /**
     * Maps BookReviewRequestDTO to BookReviewsEntity.
     * <p>
     * - Stubs BookEntity and CustomerEntity from IDs.
     * - Ignores system fields on creation.
     * </p>
     */
    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBook")
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomer")
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.mavrommatis.ebookshop.ebookshop.entity.BookReviewsEntity toEntity(BookReviewRequestDTO dto);

    /**
     * Maps BookReviewsEntity to BookReviewResponseDTO.
     */
    BookReviewResponseDTO toResponse(com.mavrommatis.ebookshop.ebookshop.entity.BookReviewsEntity entity);

    /**
     * Creates a stub BookEntity based on its ID.
     */
    @Named("mapBook")
    default BookEntity mapBook(Integer id) {
        if (id == null) return null;
        BookEntity b = new BookEntity(); b.setBookId(id); return b;
    }

    /**
     * Creates a stub CustomerEntity based on its ID.
     */
    @Named("mapCustomer")
    default com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity mapCustomer(Integer id) {
        if (id == null) return null;
        com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity c = new com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity();
        c.setCustomerId(id);
        return c;
    }
}
