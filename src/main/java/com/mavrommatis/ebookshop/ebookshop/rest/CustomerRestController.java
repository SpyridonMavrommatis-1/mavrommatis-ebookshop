package com.mavrommatis.ebookshop.ebookshop.rest;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerRestController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRestController.class);

    private final CustomerRepository customerRepository;

    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/api/customers")
    public List<Customer> getAllCustomers(){
        logger.info("GET /api/customers called");

        List<Customer> customers = customerRepository.findAll();
        logger.debug("Number of customer fetched: {}", customers.size());

        return customers;
    }
}
