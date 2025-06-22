package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.request.BookReviewsRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookReviewsResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookReviewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface BookReviewsMapper {

    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBook")
    @Mapping(target = "reviewId", ignore = true)
    @Mapping(target = "customer", ignore = true) // will be set manually
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    BookReviewsEntity toEntity(BookReviewsRequestDTO dto);

    @Mapping(source = "book.bookId", target = "bookId")
    @Mapping(source = "customer.customerId", target = "customerId")
    BookReviewsResponseDTO toResponse(BookReviewsEntity entity);

    @Named("mapBook")
    default BookEntity mapBook(Integer id) {
        if (id == null) return null;
        BookEntity b = new BookEntity(); b.setBookId(id); return b;
    }
}
