package com.bilge.cgaccountdemo.repo;

import com.bilge.cgaccountdemo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {}
