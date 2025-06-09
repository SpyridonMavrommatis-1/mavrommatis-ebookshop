package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link CustomerService} interface.
 * Handles business logic for managing {@link CustomerEntity} entities.
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Constructor for injecting the {@link CustomerRepository}.
     *
     * @param customerRepository the repository for customer data access
     */
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Retrieve all customers from the database.
     *
     * @return a list of all {@link CustomerEntity} entities
     */
    @Override
    public List<CustomerEntity> findAll() {
        return customerRepository.findAll();
    }

    /**
     * Find a customer by ID.
     *
     * @param id the customer ID
     * @return an {@link Optional} containing the customer if found, otherwise empty
     */
    @Override
    public Optional<CustomerEntity> findById(Integer id) {
        return customerRepository.findById(id);
    }

    /**
     * Save a new customer if they do not already exist.
     *
     * @param customer the {@link CustomerEntity} entity to save
     * @return the saved {@link CustomerEntity}
     * @throws RuntimeException if a customer with the same ID already exists
     */
    @Override
    public CustomerEntity save(CustomerEntity customer) {
        if (customer.getCustomerId() != 0 && customerRepository.existsById(customer.getCustomerId())) {
            throw new RuntimeException("Customer already exists with id: " + customer.getCustomerId());
        }
        return customerRepository.save(customer);
    }

    /**
     * Save a list of customers if none of them already exist.
     *
     * @param customers a list of {@link CustomerEntity} entities to save
     * @return a list of saved {@link CustomerEntity} entities
     * @throws RuntimeException if any customer already exists
     */
    @Override
    public List<CustomerEntity> saveAll(List<CustomerEntity> customers) {
        for (CustomerEntity customer : customers) {
            if (customer.getCustomerId() != 0 && customerRepository.existsById(customer.getCustomerId())) {
                throw new RuntimeException("Customer already exists with id: " + customer.getCustomerId());
            }
        }
        return customerRepository.saveAll(customers);
    }

    /**
     * Delete a customer by ID if they exist.
     *
     * @param id the customer ID
     * @throws RuntimeException if no customer is found with the given ID
     */
    @Override
    public void deleteById(Integer id) {
        Optional<CustomerEntity> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    /**
     * Delete multiple customers by ID. Fails if any ID is not found.
     *
     * @param ids the list of customer IDs to delete
     * @throws RuntimeException if any ID is not found
     */
    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
            } else {
                throw new RuntimeException("Customer not found with id: " + id);
            }
        }
    }
}
