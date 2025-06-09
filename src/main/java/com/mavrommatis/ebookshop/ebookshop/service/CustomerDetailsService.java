package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link CustomerDetailsEntity} entities.
 * Provides methods for retrieving, saving, and deleting customer detail records.
 */
public interface CustomerDetailsService {

    /**
     * Retrieve all customer details from the database.
     *
     * @return a list of all {@link CustomerDetailsEntity}
     */
    List<CustomerDetailsEntity> findAll();

    /**
     * Find customer details by their ID.
     *
     * @param id the ID of the customer details record
     * @return an {@link Optional} containing the customer details if found, or empty otherwise
     */
    Optional<CustomerDetailsEntity> findById(Integer id);

    /**
     * Save a new customer details entry to the database.
     *
     * @param customerDetails the customer details entity to save
     * @return the saved {@link CustomerDetailsEntity}
     */
    CustomerDetailsEntity save(CustomerDetailsEntity customerDetails);

    /**
     * Save a list of customer details entries to the database.
     *
     * @param customerDetails a list of customer details entities to save
     * @return the saved list of {@link CustomerDetailsEntity}
     */
    List<CustomerDetailsEntity> saveAll(List<CustomerDetailsEntity> customerDetails);

    /**
     * Delete a customer details record by its ID if it exists.
     *
     * @param id the ID of the customer details to delete
     */
    void deleteById(Integer id);

    /**
     * Delete multiple customer details records by their IDs if they exist.
     *
     * @param ids a list of IDs to delete
     */
    void deleteAllById(List<Integer> ids);
}
