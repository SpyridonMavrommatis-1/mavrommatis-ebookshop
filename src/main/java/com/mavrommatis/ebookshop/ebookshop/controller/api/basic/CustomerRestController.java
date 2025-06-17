package com.mavrommatis.ebookshop.ebookshop.controller.api.basic;

import com.mavrommatis.ebookshop.ebookshop.dto.request.CustomerRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.CustomerResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.service.basic.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *     REST controller responsible for managing customers
 *     using {@link CustomerRequestDTO} and {@link CustomerResponseDTO}.
 * </p>
 *<p>
 *     Exposes CRUD and batch endpoints that operate on the
 *     {@link com.mavrommatis.ebookshop.ebookshop.entity.basic.CustomerEntity CustomerEntity},
 *     while hiding internal structure through DTOs.
 *</p>
 *<p>
 *     Role-based access is enforced via {@code @PreAuthorize}
 *     based on business requirements.
 *</p>
 *
 *
 * @see com.mavrommatis.ebookshop.ebookshop.dto.request.CustomerRequestDTO
 * @see com.mavrommatis.ebookshop.ebookshop.dto.response.CustomerResponseDTO
 * @see com.mavrommatis.ebookshop.ebookshop.service.basic.CustomerService
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
     *<p>
     *    Accessible to {@code EMPLOYEE} and {@code ADMIN} role.
     *</p>
     * @return list of {@link CustomerResponseDTO}
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<CustomerResponseDTO> findAll() {
        return customerService.findAll();
    }

    /**
     * Retrieve a customer by ID.
     * <p>
     *    Accessible to {@code CUSTOMER}, {@code EMPLOYEE} and {@code ADMIN} roles.
     * </p>
     *
     * @param customerId the customer ID
     * @return the {@link CustomerResponseDTO}
     */
    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'EMPLOYEE', 'ADMIN')")
    public ResponseEntity<CustomerResponseDTO> findById(@PathVariable Integer customerId) {
        CustomerResponseDTO dto = customerService.findById(customerId);
        return ResponseEntity.ok(dto);
    }

    /**
     * Create a new customer.
     * <p>
     *     Accessible to {@code CUSTOMER} and {@code ADMIN} role.
     * </p>
     *
     * @param request the {@link CustomerRequestDTO} containing customer data
     * @return the created {@link CustomerResponseDTO}
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody CustomerRequestDTO request) {
        CustomerResponseDTO created = customerService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing customer.
     *<p>
     *     Accessible to {@code CUSTOMER} and {@code ADMIN} role.
     *</p>
     * @param customerId the customer ID to update
     * @param request the {@link CustomerRequestDTO} containing updated data
     * @return the updated {@link CustomerResponseDTO}
     */
    @PutMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public CustomerResponseDTO updateCustomer(
            @PathVariable Integer customerId,
            @RequestBody CustomerRequestDTO request
    ) {
        return customerService.update(customerId, request);
    }

    /**
     * Delete a customer by ID.
     * <p>
     *    Accessible to {@code CUSTOMER} and {@code ADMIN} role.
     * </p>
     *
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
     * @param customerId list of customer identifiers to delete
     */
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
    public void deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteById(customerId);
    }

    /**
     * Save multiple customers in batch.
     * <p>
     *    Accessible to {@code ADMIN} role.
     * </p>
     * @param requests list of {@link CustomerRequestDTO} entries to save
     * @return list of created {@link CustomerResponseDTO}
     */
    @PostMapping("/batch")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<CustomerResponseDTO> saveAllCustomers(@RequestBody List<CustomerRequestDTO> requests) {
        return requests.stream()
                .map(customerService::save)
                .toList();
    }

    /**
     * Delete multiple customers by their IDs.
     * <p>
     *    Accessible to {@code ADMIN} role.
     * </p>
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
     * @param ids list of customer IDs to delete
     */
    @DeleteMapping("/batch")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteAllCustomers(@RequestBody List<Integer> ids) {
        customerService.deleteAllById(ids);
    }
}
