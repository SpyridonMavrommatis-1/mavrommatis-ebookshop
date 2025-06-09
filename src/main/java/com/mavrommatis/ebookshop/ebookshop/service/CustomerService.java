package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link CustomerEntity} entities.
 * Provides standard CRUD operations for customer management.
 */
public interface CustomerService {

    /**
     * Retrieve all customers from the database.
     *
     * @return a list of {@link CustomerEntity} entities
     */
    List<CustomerEntity> findAll();

    /**
     * Find a customer by their unique ID.
     *
     * @param id the customer ID
     * @return an {@link Optional} containing the customer if found, or empty if not
     */
    Optional<CustomerEntity> findById(Integer id);

    /**
     * Save a new customer if they do not already exist.
     *
     * @param customer the customer to be saved
     * @return the saved {@link CustomerEntity} entity
     */
    CustomerEntity save(CustomerEntity customer);

    /**
     * Save a list of customers. If any of them already exist, an exception should be thrown in implementation.
     *
     * @param customers the list of customers to save
     * @return the list of saved {@link CustomerEntity} entities
     */
    List<CustomerEntity> saveAll(List<CustomerEntity> customers);

    /**
     * Delete a customer by their ID.
     *
     * @param id the ID of the customer to delete
     */
    void deleteById(Integer id);

    /**
     * Delete multiple customers by their IDs.
     *
     * @param ids a list of IDs corresponding to the customers to delete
     */
    void deleteAllById(List<Integer> ids);
}
