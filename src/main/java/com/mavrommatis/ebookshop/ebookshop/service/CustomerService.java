package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing {@link Customer} entities.
 * Provides standard CRUD operations for customer management.
 */
public interface CustomerService {

    /**
     * Retrieve all customers from the database.
     *
     * @return a list of {@link Customer} entities
     */
    List<Customer> findAll();

    /**
     * Find a customer by their unique ID.
     *
     * @param id the customer ID
     * @return an {@link Optional} containing the customer if found, or empty if not
     */
    Optional<Customer> findById(Integer id);

    /**
     * Save a new customer if they do not already exist.
     *
     * @param customer the customer to be saved
     * @return the saved {@link Customer} entity
     */
    Customer save(Customer customer);

    /**
     * Save a list of customers. If any of them already exist, an exception should be thrown in implementation.
     *
     * @param customers the list of customers to save
     * @return the list of saved {@link Customer} entities
     */
    List<Customer> saveAll(List<Customer> customers);

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
