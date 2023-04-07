package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.model.AccountStatement;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService{
	List<Account> findAll();
	List<Account> findAccountsByCustomerID(Long customerID);
	Account save(Account account);
	Account findById(Long accountID);
	Boolean addToBalance(Long accountID, BigDecimal creditAmount);
	Boolean substractFromBalance(Long accountID, BigDecimal withdrawAmount);
	List<AccountStatement> getStatements(Long customerID);
}
