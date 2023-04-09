package com.bilge.cgaccountdemo.entities;

import com.bilge.cgaccountdemo.configs.TransactionType;
import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Transaction {
	private @Id @GeneratedValue Long transactionID;
	private Long accountID;
	private BigDecimal transactionAmount;
	private TransactionType transactionType;
	public Timestamp transactionDatetime;

	public Transaction(){}

	public Transaction(Long accountID, BigDecimal transactionAmount, TransactionType transactionType){
		this.accountID = accountID;
		this.transactionAmount = transactionAmount;
		this.transactionType = transactionType;
	}

	public Long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Timestamp getTransactionDatetime() {
		return transactionDatetime;
	}

	public void setTransactionDatetime(Timestamp transactionDatetime) {
		this.transactionDatetime = transactionDatetime;
	}
}
