package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.entities.Customer;

import java.util.List;

public interface CustomerService {
	List<Customer> findAll();
	Customer save(Customer customer);
	Customer findById(Long customerID);
}
