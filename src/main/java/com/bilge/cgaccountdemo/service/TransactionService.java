package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;
import com.bilge.cgaccountdemo.entities.Transaction;

import java.util.List;

public interface TransactionService {
	List<Transaction> findTransactionsByAccountID(Long customerID);
	Transaction save(Transaction transaction);
	Transaction addCreadit(CreditBalanceRequest creditBalanceRequest);
	Transaction findById(Long transactionID);
}
