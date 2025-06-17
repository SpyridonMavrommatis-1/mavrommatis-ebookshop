package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * MapStruct mapper interface for converting between {@link BookReviewsEntity} and
 * its corresponding DTOs used in client-server communication.
 *
 * <p>This mapper handles bidirectional transformation between:
 * <ul>
 *   <li>{@link BookReviewsRequestDTO} → {@link BookReviewsEntity} (for persisting client reviews)</li>
 *   <li>{@link BookReviewsEntity} → {@link BookReviewsResponseDTO} (for exposing review data)</li>
 * </ul>
 *
 * <p>Special mapping logic includes:
 * <ul>
 *   <li>Injecting {@link BookEntity} and {@link CustomerEntity} as stubs via their IDs</li>
 *   <li>Ignoring system-managed fields (e.g., {@code reviewId}, {@code createdAt},
 *   {@code updatedAt}) when mapping from request</li>
 * </ul>
 *
 * <p>This interface is registered as a Spring component and is used inside service
 * layers to separate entity logic from data exchange layers.</p>
 *
 * @see BookReviewsEntity
 * @see BookReviewsRequestDTO
 * @see BookReviewsResponseDTO
 * @see BookEntity
 * @see CustomerEntity
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
    BookReviewsEntity toEntity(BookReviewsRequestDTO dto);

    /**
     * Maps BookReviewsEntity to BookReviewResponseDTO.
     */
    BookReviewsResponseDTO toResponse(BookReviewsEntity entity);

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
    default CustomerEntity mapCustomer(Integer id) {
        if (id == null) return null;
        CustomerEntity c = new CustomerEntity();
        c.setCustomerId(id);
        return c;
    }
}
