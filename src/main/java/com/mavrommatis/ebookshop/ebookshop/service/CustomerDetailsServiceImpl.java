package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.CustomerDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final CustomerDetailsRepository customerDetailsRepository;


    public CustomerDetailsServiceImpl(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    @Override
    public List<CustomerDetails> findAll() {
        return customerDetailsRepository.findAll();
    }

    @Override
    public Optional<CustomerDetails> findById(Integer id) {
        return customerDetailsRepository.findById(id);
    }

    @Override
    public CustomerDetails save(CustomerDetails customerDetails) {
        if (customerDetails.getCustomerId() != 0 && customerDetailsRepository.existsById(customerDetails.getCustomerId())) {
            throw new RuntimeException("CustomerDetails already exists with id: " + customerDetails.getCustomerId());
        }
        return customerDetailsRepository.save(customerDetails);
    }

        @Override
        public List<CustomerDetails> saveAll(List<CustomerDetails> customersDetails) {
            for (CustomerDetails customerDetails : customersDetails) {
                if (customerDetails.getCustomerId() != 0 && customerDetailsRepository.existsById(customerDetails.getCustomerId())) {
                    throw new RuntimeException("CustomerDetails already exists with id: " + customerDetails.getCustomerId());
                }
            }
            return customerDetailsRepository.saveAll(customersDetails);
        }

    @Override
    public void deleteById(Integer id) {
        Optional<CustomerDetails> customerDetails = customerDetailsRepository.findById(id);

        if (customerDetails.isPresent()) {
            customerDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("CustomerDetails not found with id: " + id);
        }
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (customerDetailsRepository.existsById(id)) {
                customerDetailsRepository.deleteById(id);
            } else {
                throw new RuntimeException("BookDetails not found with id: " + id);
            }
        }
    }
}

