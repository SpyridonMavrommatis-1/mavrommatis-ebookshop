package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation class for managing {@link CustomerDetails}.
 * Provides CRUD operations using {@link CustomerDetailsRepository}.
 */
@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;

    /**
     * Constructor-based injection of the CustomerDetailsRepository.
     *
     * @param customerDetailsRepository the repository for accessing customer details
     */
    public CustomerDetailsServiceImpl(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    /**
     * Retrieve all customer details records from the database.
     *
     * @return a list of {@link CustomerDetails}
     */
    @Override
    public List<CustomerDetails> findAll() {
        return customerDetailsRepository.findAll();
    }

    /**
     * Find a customer details record by ID.
     *
     * @param id the ID of the customer
     * @return an {@link Optional} containing the customer details if found, or empty if not
     */
    @Override
    public Optional<CustomerDetails> findById(Integer id) {
        return customerDetailsRepository.findById(id);
    }

    /**
     * Save a new customer details record if it doesn't already exist.
     *
     * @param customerDetails the customer details to save
     * @return the saved {@link CustomerDetails}
     * @throws RuntimeException if a record with the same ID already exists
     */
    @Override
    public CustomerDetails save(CustomerDetails customerDetails) {
        if (customerDetails.getCustomerId() != 0 &&
                customerDetailsRepository.existsById(customerDetails.getCustomerId())) {
            throw new RuntimeException("CustomerDetails already exists with id: " + customerDetails.getCustomerId());
        }
        return customerDetailsRepository.save(customerDetails);
    }

    /**
     * Save a list of customer details, ensuring none already exist by ID.
     *
     * @param customersDetails the list of customer details to save
     * @return the saved list of {@link CustomerDetails}
     * @throws RuntimeException if any of the records already exist
     */
    @Override
    public List<CustomerDetails> saveAll(List<CustomerDetails> customersDetails) {
        for (CustomerDetails customerDetails : customersDetails) {
            if (customerDetails.getCustomerId() != 0 &&
                    customerDetailsRepository.existsById(customerDetails.getCustomerId())) {
                throw new RuntimeException("CustomerDetails already exists with id: " + customerDetails.getCustomerId());
            }
        }
        return customerDetailsRepository.saveAll(customersDetails);
    }

    /**
     * Delete a customer details record by ID, if it exists.
     *
     * @param id the ID of the customer to delete
     * @throws RuntimeException if the record is not found
     */
    @Override
    public void deleteById(Integer id) {
        Optional<CustomerDetails> customerDetails = customerDetailsRepository.findById(id);
        if (customerDetails.isPresent()) {
            customerDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("CustomerDetails not found with id: " + id);
        }
    }

    /**
     * Delete multiple customer records by ID, if they exist.
     *
     * @param ids the list of IDs to delete
     * @throws RuntimeException if any of the records are not found
     */
    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (customerDetailsRepository.existsById(id)) {
                customerDetailsRepository.deleteById(id);
            } else {
                throw new RuntimeException("CustomerDetails not found with id: " + id);
            }
        }
    }
}
