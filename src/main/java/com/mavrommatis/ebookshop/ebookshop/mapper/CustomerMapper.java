package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting between Customer entities and DTOs.
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Maps CustomerRequestDTO to CustomerEntity.
     */
    @Mapping(target = "customerDetails", source = "customerDetails")
    CustomerEntity toEntity(CustomerRequestDTO dto);

    /**
     * Maps CustomerEntity to CustomerResponseDTO.
     */
    @Mapping(target = "customerDetails", source = "customerDetails")
    CustomerResponseDTO toResponse(CustomerEntity entity);

    /**
     * Maps CustomerDetailsDTO to CustomerDetailsEntity.
     */
    CustomerDetailsEntity toEntity(CustomerDetailsDTO dto);

    /**
     * Maps CustomerDetailsEntity to CustomerDetailsDTO.
     */
    CustomerDetailsDTO toDto(CustomerDetailsEntity entity);
}