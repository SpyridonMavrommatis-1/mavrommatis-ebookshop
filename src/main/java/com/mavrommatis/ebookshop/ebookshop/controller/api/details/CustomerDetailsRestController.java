package com.mavrommatis.ebookshop.ebookshop.controller.api.details;

import com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.service.details.CustomerDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     REST controller responsible for managing books details
 *     using {@link CustomerDetailsDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.details.CustomerDetailsEntity CustomerDetails},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.details.CustomerDetailsDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.details.CustomerDetailsService
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
     * <p>
     *   Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     * </p>
     * @return list of {@link CustomerDetailsDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<CustomerDetailsDTO> findAll() {
        return customerDetailsService.findAll();
    }

    /**
     * Retrieve a specific customer detail by customer ID.
     * <p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} roles.
     * </p>
     * @param customerId the customer identifier
     * @return the {@link CustomerDetailsDTO}
     */
    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<CustomerDetailsDTO> findById(@PathVariable Integer customerId) {
        CustomerDetailsDTO dto = customerDetailsService.findById(customerId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create new customer detail record.
     * <p>
     *     Accessible to {@code CUSTOMER} and {@code ADMIN} role.
     * </p>
     * @param dto the {@link CustomerDetailsDTO} to create
     * @return the created {@link CustomerDetailsDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<CustomerDetailsDTO> create(@RequestBody @Valid CustomerDetailsDTO dto) {
        CustomerDetailsDTO created = customerDetailsService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing customer detail record by customer ID.
     * <p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} roles.
     * </p>
     * @param customerId the customer identifier
     * @param dto        the {@link CustomerDetailsDTO} with updated data
     * @return the updated {@link CustomerDetailsDTO}
     */
    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public CustomerDetailsDTO update(
            @PathVariable Integer customerId,
            @RequestBody @Valid CustomerDetailsDTO dto
    ) {
        return customerDetailsService.save(dto);
    }

    /**
     * Delete a customer detail record by customer ID.
     * <p>
     *    Accessible to {@code CUSTOMER} and {@code ADMIN} role.
     * </p>
     * <p>
     *     This method returns an HTTP 204 (No Content) status code on success, indicating that:
     * </p>
     *
     * <ul>
     *     <li>the request was successfully processed,</li>
     *     <li>but no response body is returned to the client.</li>
     * </ul>
     *
     * <p>
     *   The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *   instead of returning a {@code ResponseEntity<Void>} with status code 204
     *   This keeps the controller method signature minimal and declarative.
     * </p>
     *
     * @param customerId the ID of the customer to delete details for
     */
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public void delete(@PathVariable Integer customerId) {
        customerDetailsService.deleteById(customerId);
    }

    /**
     * Create or update multiple customer detail records in batch.
     * <p>
     *    Accessible to {@code ADMIN} role.
     * </p>
     * @param dtos list of {@link CustomerDetailsDTO} to process
     * @return list of processed {@link CustomerDetailsDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CustomerDetailsDTO> batchSave(@RequestBody @Valid List<@Valid CustomerDetailsDTO> dtos) {
        return customerDetailsService.saveAll(dtos);
    }

    /**
     * Delete multiple customer detail records by their customer IDs.
     * <p>
     * This endpoint is accessible only to users with the {@code ADMIN} role.
     * <p>
     * This method returns an HTTP 204 (No Content) status code on success, indicating that:
     *   <ul>
     *       <li>the request was successfully processed,</li>
     *       <li>but no response body is returned to the client.</li>
     *   </ul>
     * <p>
     *     The {@code @ResponseStatus(HttpStatus.NO_CONTENT)} annotation is used
     *     instead of returning a {@code ResponseEntity<Void>} with status code 204.
     *     This keeps the controller method signature minimal and declarative.
     * </p>
     * @param customerIds list of customer IDs whose details to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void batchDelete(@RequestBody List<Integer> customerIds) {
        customerDetailsService.deleteAllById(customerIds);
    }
}
