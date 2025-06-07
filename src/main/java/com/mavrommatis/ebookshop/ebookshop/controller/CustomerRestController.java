package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Customer entities.
 * Provides endpoints for CRUD operations on customers and batch processing.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    /**
     * Constructor-based dependency injection.
     *
     * @param customerService the service handling Customer logic
     */
    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of all Customer objects
     */
    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param id the ID of the customer
     * @return the found Customer
     * @throws RuntimeException if the customer does not exist
     */
    @GetMapping("/{id}")
    public Customer findById(@PathVariable int id) {
        return customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    /**
     * Creates a new customer.
     *
     * @param customer the customer object from the request body
     * @return the saved Customer
     */
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer);  // Maintain bidirectional relationship
        }
        return customerService.save(customer);
    }

    /**
     * Updates an existing customer.
     *
     * @param id       the ID of the customer to update
     * @param customer the updated customer object
     * @return the updated Customer
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer);  // Maintain relationship
        }
        return customerService.save(customer);
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return a confirmation message
     */
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteById(id);
        return "Customer with id " + id + " deleted.";
    }

    /**
     * Creates or updates a list of customers in batch.
     *
     * @param customers the list of customers to save
     * @return the list of saved customers
     */
    @PostMapping("/batch")
    public List<Customer> saveAllCustomers(@RequestBody List<Customer> customers) {
        for (Customer customer : customers) {
            CustomerDetails details = customer.getCustomerDetails();
            if (details != null) {
                details.setCustomer(customer);
            }
        }
        return customerService.saveAll(customers);
    }

    /**
     * Deletes multiple customers by their IDs.
     *
     * @param ids the list of customer IDs to delete
     * @return a confirmation message
     */
    @DeleteMapping("/batch")
    public String deleteAllCustomers(@RequestBody List<Integer> ids) {
        customerService.deleteAllById(ids);
        return "Customers with ids " + ids + " deleted.";
    }
}
