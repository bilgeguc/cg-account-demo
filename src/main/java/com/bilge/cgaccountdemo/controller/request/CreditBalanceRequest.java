package com.bilge.cgaccountdemo.controller.request;

import com.bilge.cgaccountdemo.configs.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditBalanceRequest {
	private Long accountID;
	private Long customerID;
	private BigDecimal transactionAmount;
	private TransactionType transactionType = TransactionType.CREDIT;

	public CreditBalanceRequest(Long customerID, Long accountID, BigDecimal transactionAmount){
		this.customerID = customerID;
		this.accountID = accountID;
		this.transactionAmount = transactionAmount;
		if(transactionAmount.compareTo(BigDecimal.ZERO) == 0){
			this.transactionType = TransactionType.ZERO;
		}else if(transactionAmount.compareTo(BigDecimal.ZERO) < 0){
			this.transactionType = TransactionType.WITHDRAW;
		}
	}

	public Boolean isZero(){
		return this.transactionType == TransactionType.ZERO;
	}

	public Boolean isWithdraw(){
		return this.transactionType == TransactionType.WITHDRAW;
	}
}
