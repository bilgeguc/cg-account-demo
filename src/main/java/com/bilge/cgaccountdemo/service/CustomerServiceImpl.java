package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.entities.Customer;
import com.bilge.cgaccountdemo.repo.CustomerRepository;
import com.bilge.cgaccountdemo.responseerrors.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	public Customer save(Customer customer) {
		customerRepository.save(customer);
		return customerRepository.findById(customer.getCustomerId()).get();
	}

	@Override
	public Customer findById(Long customerID) {
		return customerRepository.findById(customerID).orElseThrow(() -> new CustomerNotFoundException(customerID));
	}
}
