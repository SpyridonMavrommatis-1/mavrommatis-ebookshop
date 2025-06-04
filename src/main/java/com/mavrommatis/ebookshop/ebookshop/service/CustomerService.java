package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    //See BookService notes

    List<Customer> findAll();


    Optional<Customer> findById(Integer id);


    Customer save(Customer customer);


    public List<Customer> saveAll(List<Customer> customers);


    void deleteById(Integer id);


    public void deleteAllById(List<Integer> ids);

}
