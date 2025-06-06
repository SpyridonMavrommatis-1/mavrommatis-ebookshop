package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerdetails")
public class CustomerDetailsRestController {

    private final CustomerDetailsService customerDetailsService;

    @Autowired
    public CustomerDetailsRestController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    // GET /api/customerdetails
    @GetMapping
    public List<CustomerDetails> findAll() {
        return customerDetailsService.findAll();
    }

    // GET /api/customerdetails/{id}
    @GetMapping("/{id}")
    public CustomerDetails findById(@PathVariable int id) {
        return customerDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found with id: " + id));
    }

    // POST /api/customerdetails
    @PostMapping
    public CustomerDetails createCustomerDetails(@RequestBody CustomerDetails customerDetails) {
        return customerDetailsService.save(customerDetails);
    }

    // PUT /api/customerdetails/{id}
    @PutMapping("/{id}")
    public CustomerDetails updateCustomerDetails(@PathVariable int id, @RequestBody CustomerDetails customerDetails) {
        customerDetails.setCustomerId(id);
        return customerDetailsService.save(customerDetails);
    }

    // POST /api/customerdetails/batch
    @PostMapping("/batch")
    public List<CustomerDetails> saveAllCustomerDetails(@RequestBody List<CustomerDetails> customerDetailsList) {
        return customerDetailsService.saveAll(customerDetailsList);
    }

    // DELETE /api/customerdetails/{id}
    @DeleteMapping("/{id}")
    public String deleteCustomerDetails(@PathVariable int id) {
        customerDetailsService.deleteById(id);
        return "CustomerDetails with id " + id + " deleted.";
    }

    // DELETE /api/customerdetails/batch
    @DeleteMapping("/batch")
    public String deleteAllCustomerDetails(@RequestBody List<Integer> ids) {
        customerDetailsService.deleteAllById(ids);
        return "CustomerDetails with ids " + ids + " deleted.";
    }
}
