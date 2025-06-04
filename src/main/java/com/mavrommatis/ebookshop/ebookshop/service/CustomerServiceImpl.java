package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    //See BookServiceImpl notes

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        if (customer.getCustomerId() != 0 && customerRepository.existsById(customer.getCustomerId())) {
            throw new RuntimeException("Customer already exists with id: " + customer.getCustomerId());
        }
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> saveAll(List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getCustomerId() != 0 && customerRepository.existsById(customer.getCustomerId())) {
                throw new RuntimeException("Customer already exists with id: " + customer.getCustomerId());
            }
        }
        return customerRepository.saveAll(customers);
    }

    @Override
    public void deleteById(Integer id) {

        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        } else {
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }


    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (customerRepository.existsById(id)) {
                customerRepository.deleteById(id);
            } else {
                throw new RuntimeException("Customer not found with id: " + id);
            }
        }
    }
}
