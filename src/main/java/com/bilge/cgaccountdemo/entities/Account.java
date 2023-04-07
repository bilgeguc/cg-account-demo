package com.bilge.cgaccountdemo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Account {
	private @Id @GeneratedValue Long accountID;
	private Long customerID;
	private BigDecimal Balance = BigDecimal.ZERO;

	public Account(){}

	public Account(Long customerID) {
		this.customerID = customerID;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public BigDecimal getBalance() {
		return Balance;
	}

	public void setBalance(BigDecimal balance) {
		Balance = balance;
	}

	public void addBalance(BigDecimal creditToAdd){
		Balance = Balance.add(creditToAdd);
	}

	public void withdrawBalance(BigDecimal creditToWithdraw){
		Balance = Balance.subtract(creditToWithdraw);
	}
}
