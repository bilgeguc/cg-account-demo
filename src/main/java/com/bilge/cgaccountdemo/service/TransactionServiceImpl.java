package com.bilge.cgaccountdemo.service;

import com.bilge.cgaccountdemo.configs.TransactionType;
import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;
import com.bilge.cgaccountdemo.entities.Transaction;
import com.bilge.cgaccountdemo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public List<Transaction> findTransactionsByAccountID(Long customerID) {
		return transactionRepository.findByAccountIDIs(customerID);
	}

	@Override
	public Transaction save(Transaction transaction) {
		transactionRepository.save(transaction);
		return transactionRepository.findById(transaction.getTransactionID()).get();
	}

	@Override
	public Transaction addCreadit(CreditBalanceRequest creditBalanceRequest) {
		Transaction transaction = new Transaction(creditBalanceRequest.getAccountID(), creditBalanceRequest.getTransactionAmount(), TransactionType.CREDIT);
		transaction.setTransactionDatetime(new Timestamp(System.currentTimeMillis()));
		if(!creditBalanceRequest.isZero()){
			save(transaction);
		}else{
			transaction.setTransactionType(TransactionType.ZERO);
		}
		return transaction;
	}

	@Override
	public Transaction findById(Long transactionID) {
		return transactionRepository.findById(transactionID).get();
	}

	public List<Transaction> listTransactionsByDatetime(Long accountID){
		List<Transaction> transactions = transactionRepository.findByAccountIDIs(accountID);
		return transactions.stream()
				.sorted()
				.collect(Collectors.toList());
	}
}
