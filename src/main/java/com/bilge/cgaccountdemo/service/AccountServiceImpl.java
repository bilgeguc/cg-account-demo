package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.entities.Transaction;
import com.bilge.cgaccountdemo.model.AccountStatement;
import com.bilge.cgaccountdemo.repo.AccountRepository;
import com.bilge.cgaccountdemo.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	TransactionService transactionService;

	public AccountServiceImpl() {}

	@Override
	public Account save(Account account) {
		accountRepository.save(account);
		return accountRepository.findById(account.getAccountID()).get();
	}

	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public List<Account> findAccountsByCustomerID(Long customerID) {
		return accountRepository.findAccountsByCustomerIDIs(customerID);
	}

	@Override
	public Account findById(Long accountID) {
		Account account = accountRepository.findById(accountID).get();
		return account;
	}

	@Override
	public Boolean addToBalance(Long accountID, BigDecimal creditAmount) {
		if(creditAmount.compareTo(BigDecimal.ZERO) > 0){
			Account account = accountRepository.findById(accountID).get();
			account.addBalance(creditAmount);
			accountRepository.save(account);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Boolean substractFromBalance(Long accountID, BigDecimal withdrawAmount) {
		if(withdrawAmount.compareTo(BigDecimal.ZERO) > 0){
			Account account = accountRepository.findById(accountID).get();
			account.withdrawBalance(withdrawAmount);
			accountRepository.save(account);
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<AccountStatement> getStatements(Long customerID){
		List<Account> accounts = accountRepository.findAccountsByCustomerIDIs(customerID);
		List<AccountStatement> accountStatements = new ArrayList<>();
		for(Account account : accounts){
			List<Transaction> transactions = transactionService.findTransactionsByAccountID(account.getAccountID());
			AccountStatement accountStatement = new AccountStatement(account, transactions);
			accountStatements.add(accountStatement);
		}
		return accountStatements;
	}
}