package com.bilge.cgaccountdemo.repo;

import com.bilge.cgaccountdemo.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	List<Transaction> findByAccountIDIs(Long accountID);
}
