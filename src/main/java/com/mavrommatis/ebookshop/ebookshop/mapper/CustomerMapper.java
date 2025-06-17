package com.mavrommatis.ebookshop.ebookshop.mapper;

import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.request.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between {@link CustomerEntity} domain entities and
 * their corresponding Data Transfer Objects (DTOs).
 *
 * <p>This interface is implemented at build-time by MapStruct and is used by the service
 * layer to handle transformations between the persistence model and the API layer.</p>
 *
 * <p>Supported transformations include:</p>
 * <ul>
 *   <li>{@link CustomerRequestDTO} → {@link CustomerEntity} (for incoming HTTP requests)</li>
 *   <li>{@link CustomerEntity} → {@link CustomerResponseDTO} (for outgoing HTTP responses)</li>
 *   <li>{@link CustomerDetailsDTO} ↔ {@link CustomerDetailsEntity} (for nested profile data)</li>
 * </ul>
 *
 * <p>This mapper handles both the primary entity and its associated one-to-one details component.
 * Mapping is field-based and assumes matching property names between DTOs and entities.</p>
 *
 * @see CustomerRequestDTO
 * @see CustomerResponseDTO
 * @see CustomerDetailsDTO
 * @see CustomerEntity
 * @see CustomerDetailsEntity
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