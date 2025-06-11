package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dto.CustomerDetailsDTO;
import java.util.List;

/**
 * Service interface for managing customer detail data via DTOs.
 * <p>
 * Provides CRUD operations for customer profile information,
 * decoupling persistence entities from the client API.
 */
public interface CustomerDetailsService {

    /**
     * Retrieve all customer detail records.
     *
     * @return list of {@link CustomerDetailsDTO} representing all profiles
     */
    List<CustomerDetailsDTO> findAll();

    /**
     * Retrieve a single customer detail by its customer ID.
     *
     * @param customerId the unique identifier of the customer
     * @return the corresponding {@link CustomerDetailsDTO}
     * @throws RuntimeException if no record is found for the given ID
     */
    CustomerDetailsDTO findById(Integer customerId);

    /**
     * Create or update customer detail information.
     * <p>
     * If a record exists for the given ID, it is updated; otherwise a new record is created.
     * </p>
     *
     * @param dto the {@link CustomerDetailsDTO} containing detail data
     * @return the persisted {@link CustomerDetailsDTO}
     */
    CustomerDetailsDTO save(CustomerDetailsDTO dto);

    /**
     * Batch create or update multiple customer detail records.
     *
     * @param dtos list of {@link CustomerDetailsDTO} to persist
     * @return list of persisted {@link CustomerDetailsDTO}
     */
    List<CustomerDetailsDTO> saveAll(List<CustomerDetailsDTO> dtos);

    /**
     * Delete a customer detail record by its customer ID.
     *
     * @param customerId the ID of the customer to delete details for
     * @throws RuntimeException if no record exists for the given ID
     */
    void deleteById(Integer customerId);

    /**
     * Delete multiple customer detail records by their customer IDs.
     *
     * @param customerIds list of customer IDs whose details to delete
     * @throws RuntimeException if any provided ID is not found
     */
    void deleteAllById(List<Integer> customerIds);
}
