package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerResponseDTO;
import java.util.List;

/**
 * Service interface for managing Customer operations via DTOs.
 * <p>
 * Defines methods for CRUD operations that accept and return DTOs,
 * abstracting away persistence details from the client.
 */
public interface CustomerService {

    /**
     * Retrieve all customers.
     *
     * @return list of {@link CustomerResponseDTO}
     */
    List<CustomerResponseDTO> findAll();

    /**
     * Retrieve a specific customer by ID.
     *
     * @param id customer identifier
     * @return {@link CustomerResponseDTO} of the found customer
     * @throws RuntimeException if no customer is found
     */
    CustomerResponseDTO findById(Integer id);

    /**
     * Create a new customer.
     *
     * @param dto the {@link CustomerRequestDTO} containing customer data
     * @return {@link CustomerResponseDTO} of the created customer
     */
    CustomerResponseDTO save(CustomerRequestDTO dto);

    /**
     * Update an existing customer.
     *
     * @param id  the ID of the customer to update
     * @param dto the {@link CustomerRequestDTO} containing updated data
     * @return {@link CustomerResponseDTO} of the updated customer
     * @throws RuntimeException if no customer exists with the given ID
     */
    CustomerResponseDTO update(Integer id, CustomerRequestDTO dto);

    /**
     * Delete a customer by ID.
     *
     * @param id customer identifier
     * @throws RuntimeException if no customer exists with the given ID
     */
    void deleteById(Integer id);

    /**
     * Delete multiple customers by their IDs.
     *
     * @param ids list of customer identifiers
     * @throws RuntimeException if any ID is not found
     */
    void deleteAllById(List<Integer> ids);
}
