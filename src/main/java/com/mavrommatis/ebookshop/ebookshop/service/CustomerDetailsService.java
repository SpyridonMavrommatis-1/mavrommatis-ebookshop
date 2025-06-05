package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;

import java.util.List;
import java.util.Optional;

public interface CustomerDetailsService {

    //See BookDetailsService notes
    List<CustomerDetails> findAll();


    Optional<CustomerDetails> findById(Integer id);


    CustomerDetails save(CustomerDetails customerDetails);


    List<CustomerDetails> saveAll(List<CustomerDetails> customerDetails);


    void deleteById(Integer id);


   void deleteAllById(List<Integer> ids);
}

