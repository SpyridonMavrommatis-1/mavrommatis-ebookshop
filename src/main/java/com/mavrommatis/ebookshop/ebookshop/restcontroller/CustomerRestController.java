package com.mavrommatis.ebookshop.ebookshop.restcontroller;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /api/customers
    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    // GET /api/customers/{id}
    @GetMapping("/{id}")
    public Customer findById(@PathVariable int id) {
        return customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    // POST /api/customers
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer);  // maintain bidirectional relationship
        }
        return customerService.save(customer);
    }

    // PUT /api/customers/{id}
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable int id, @RequestBody Customer customer) {
        customer.setCustomerId(id);
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer);  // maintain relationship
        }
        return customerService.save(customer);
    }

    // DELETE /api/customers/{id}
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteById(id);
        return "Customer with id " + id + " deleted.";
    }

    // POST /api/customers/batch
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

    // DELETE /api/customers/batch
    @DeleteMapping("/batch")
    public String deleteAllCustomers(@RequestBody List<Integer> ids) {
        customerService.deleteAllById(ids);
        return "Customers with ids " + ids + " deleted.";
    }
}
