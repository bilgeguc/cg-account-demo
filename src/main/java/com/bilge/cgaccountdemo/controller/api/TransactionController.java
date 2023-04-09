package com.bilge.cgaccountdemo.controller.api;

import com.bilge.cgaccountdemo.configs.LoadSampleData;
import com.bilge.cgaccountdemo.configs.TransactionType;
import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;
import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.entities.Transaction;
import com.bilge.cgaccountdemo.service.AccountService;
import com.bilge.cgaccountdemo.service.CustomerService;
import com.bilge.cgaccountdemo.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TransactionController {
	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);
	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value="/transaction/credit", method = RequestMethod.POST)
	public ResponseEntity<Object> creditTransaction(HttpServletRequest request,
			@RequestParam("accountID") Long accountID,
			@RequestParam("credit") BigDecimal credit){

		Map<String,Object> responseObject = new HashMap<>();
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		responseObject.put("result",false);
		responseObject.put("message","Invalid request");

		Account account = accountService.findById(accountID);
		if(null != account){
			CreditBalanceRequest creditBalanceRequest = new CreditBalanceRequest(account.getCustomerID(), account.getAccountID(), credit);
			Transaction transaction = transactionService.addCreadit(creditBalanceRequest);
			if(transaction.getTransactionType() == TransactionType.CREDIT){
				accountService.addToBalance(account.getAccountID(), credit);
				responseObject.put("result",true);
				responseObject.put("message","Transaction is successfull");
				responseStatus = HttpStatus.CREATED;
			}else{
				responseObject.put("message","Transaction request must be greater than zero.");
			}
		}else{
			responseObject.put("message","Account could not be found.");
		}
		return new ResponseEntity<>(responseObject, responseStatus);
	}
}
