package com.bilge.cgaccountdemo.repo;

import com.bilge.cgaccountdemo.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
	List<Account> findAccountsByCustomerIDIs(Long customerId);
}
