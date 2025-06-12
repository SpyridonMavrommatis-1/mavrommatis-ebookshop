package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customers via DTOs.
 * <p>
 * Provides endpoints for CRUD operations and batch processing.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    /**
     * Constructor injection for CustomerService.
     *
     * @param customerService the service handling customer operations
     */
    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Retrieve all customers.
     *
     * @return list of {@link CustomerResponseDTO}
     */
    @GetMapping
    public List<CustomerResponseDTO> findAll() {
        return customerService.findAll();
    }

    /**
     * Retrieve a customer by ID.
     *
     * @param customerId the customer ID
     * @return the {@link CustomerResponseDTO}
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Integer customerId) {
        CustomerResponseDTO dto = customerService.findById(customerId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new customer.
     *
     * @param request the {@link CustomerRequestDTO} containing customer data
     * @return the created {@link CustomerResponseDTO}
     */
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO created = customerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing customer.
     *
     * @param customerId the customer ID to update
     * @param request the {@link CustomerRequestDTO} containing updated data
     * @return the updated {@link CustomerResponseDTO}
     */
    @PutMapping("/{customerId}")
    public CustomerResponseDTO updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequestDTO request
    ) {
        return customerService.update(customerId, request);
    }

    /**
     * Delete a customer by ID.
     */
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteById(customerId);
    }

    /**
     * Save multiple customers in batch.
     *
     * @param requests list of {@link CustomerRequestDTO} entries to save
     * @return list of created {@link CustomerResponseDTO}
     */
    @PostMapping("/batch")
    public List<CustomerResponseDTO> saveAllCustomers(@RequestBody List<CustomerRequestDTO> requests) {
        return requests.stream()
                .map(customerService::save)
                .toList();
    }

    /**
     * Delete multiple customers by their IDs.
     *
     * @param ids list of customer IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCustomers(@RequestBody List<Integer> ids) {
        customerService.deleteAllById(ids);
    }
}
