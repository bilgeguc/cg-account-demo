package com.bilge.cgaccountdemo.model;

import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.entities.Transaction;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AccountStatement {
	private Account account;
	private List<Transaction> transactions = new ArrayList<>();

	public AccountStatement(Account account, List<Transaction> transactions){
		this.account = account;
		this.transactions = transactions;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
}
