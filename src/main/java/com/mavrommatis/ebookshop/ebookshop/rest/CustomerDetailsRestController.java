package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dto.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customer details via DTOs.
 * <p>
 * Exposes endpoints for CRUD and batch operations on customer profiles.
 */
@RestController
@RequestMapping("/api/customer-details")
public class CustomerDetailsRestController {

    private final CustomerDetailsService customerDetailsService;

    /**
     * Constructor injection of CustomerDetailsService.
     *
     * @param customerDetailsService service handling customer details operations
     */
    @Autowired
    public CustomerDetailsRestController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Retrieve all customer detail records.
     *
     * @return list of {@link CustomerDetailsDTO}
     */
    @GetMapping
    public List<CustomerDetailsDTO> findAll() {
        return customerDetailsService.findAll();
    }

    /**
     * Retrieve a specific customer detail by customer ID.
     *
     * @param customerId the customer identifier
     * @return the {@link CustomerDetailsDTO}
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDetailsDTO> findById(@PathVariable Integer customerId) {
        CustomerDetailsDTO dto = customerDetailsService.findById(customerId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create new customer detail record.
     *
     * @param dto the {@link CustomerDetailsDTO} to create
     * @return the created {@link CustomerDetailsDTO}
     */
    @PostMapping
    public ResponseEntity<CustomerDetailsDTO> create(@RequestBody CustomerDetailsDTO dto) {
        CustomerDetailsDTO created = customerDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing customer detail record by customer ID.
     *
     * @param customerId the customer identifier
     * @param dto        the {@link CustomerDetailsDTO} with updated data
     * @return the updated {@link CustomerDetailsDTO}
     */
    @PutMapping("/{customerId}")
    public CustomerDetailsDTO update(
            @PathVariable Integer customerId,
            @RequestBody CustomerDetailsDTO dto
    ) {
        // Ensure ID consistency if DTO contains id at all
        return customerDetailsService.save(dto);
    }

    /**
     * Delete a customer detail record by customer ID.
     *
     * @param customerId the ID of the customer to delete details for
     */
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer customerId) {
        customerDetailsService.deleteById(customerId);
    }

    /**
     * Create or update multiple customer detail records in batch.
     *
     * @param dtos list of {@link CustomerDetailsDTO} to process
     * @return list of processed {@link CustomerDetailsDTO}
     */
    @PostMapping("/batch")
    public List<CustomerDetailsDTO> batchSave(@RequestBody List<CustomerDetailsDTO> dtos) {
        return customerDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple customer detail records by their customer IDs.
     *
     * @param customerIds list of customer IDs whose details to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@RequestBody List<Integer> customerIds) {
        customerDetailsService.deleteAllById(customerIds);
    }
}
