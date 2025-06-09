package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing CustomerDetails.
 * Provides CRUD operations and batch actions through RESTful endpoints.
 */
@RestController
@RequestMapping("/api/customerdetails")
public class CustomerDetailsRestController {

    private final CustomerDetailsService customerDetailsService;

    /**
     * Constructor for dependency injection.
     *
     * @param customerDetailsService the service for handling CustomerDetails operations
     */
    @Autowired
    public CustomerDetailsRestController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Retrieves all CustomerDetails.
     *
     * @return a list of all CustomerDetails
     */
    @GetMapping
    public List<CustomerDetailsEntity> findAll() {
        return customerDetailsService.findAll();
    }

    /**
     * Retrieves a CustomerDetails by ID.
     *
     * @param id the ID of the CustomerDetails
     * @return the CustomerDetails with the given ID
     * @throws RuntimeException if not found
     */
    @GetMapping("/{id}")
    public CustomerDetailsEntity findById(@PathVariable int id) {
        return customerDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found with id: " + id));
    }

    /**
     * Creates a new CustomerDetails entry.
     *
     * @param customerDetails the CustomerDetails to create
     * @return the created CustomerDetails
     */
    @PostMapping
    public CustomerDetailsEntity createCustomerDetails(@RequestBody CustomerDetailsEntity customerDetails) {
        return customerDetailsService.save(customerDetails);
    }

    /**
     * Updates an existing CustomerDetails by ID.
     *
     * @param id              the ID of the CustomerDetails to update
     * @param customerDetails the updated CustomerDetails data
     * @return the updated CustomerDetails
     */
    @PutMapping("/{id}")
    public CustomerDetailsEntity updateCustomerDetails(@PathVariable int id, @RequestBody CustomerDetailsEntity customerDetails) {
        customerDetails.setCustomerId(id);
        return customerDetailsService.save(customerDetails);
    }

    /**
     * Saves multiple CustomerDetails entries in batch.
     *
     * @param customerDetailsList the list of CustomerDetails to save
     * @return the saved list of CustomerDetails
     */
    @PostMapping("/batch")
    public List<CustomerDetailsEntity> saveAllCustomerDetails(@RequestBody List<CustomerDetailsEntity> customerDetailsList) {
        return customerDetailsService.saveAll(customerDetailsList);
    }

    /**
     * Deletes a CustomerDetails entry by ID.
     *
     * @param id the ID of the CustomerDetails to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{id}")
    public String deleteCustomerDetails(@PathVariable int id) {
        customerDetailsService.deleteById(id);
        return "CustomerDetails with id " + id + " deleted.";
    }

    /**
     * Deletes multiple CustomerDetails entries by their IDs.
     *
     * @param ids the list of IDs to delete
     * @return a confirmation message
     */
    @DeleteMapping("/batch")
    public String deleteAllCustomerDetails(@RequestBody List<Integer> ids) {
        customerDetailsService.deleteAllById(ids);
        return "CustomerDetails with ids " + ids + " deleted.";
    }
}
