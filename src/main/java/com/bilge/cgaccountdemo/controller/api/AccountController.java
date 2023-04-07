package com.bilge.cgaccountdemo.controller.api;

import com.bilge.cgaccountdemo.configs.TransactionType;
import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;
import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.entities.Customer;
import com.bilge.cgaccountdemo.entities.Transaction;
import com.bilge.cgaccountdemo.model.AccountStatement;
import com.bilge.cgaccountdemo.service.AccountService;
import com.bilge.cgaccountdemo.service.CustomerService;
import com.bilge.cgaccountdemo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(value="/account/new", method = RequestMethod.POST)
	public ResponseEntity<Object> createNewAccount(HttpServletRequest request,
			@RequestParam("customerID") Long customerID,
			@RequestParam("initialCredit") BigDecimal initialCredit){

		Map<String,Object> responseObject = new HashMap<>();
		HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
		responseObject.put("result",false);
		responseObject.put("message","Invalid request");

		String authType = request.getAuthType(); // not necessary for now

		Customer customer = customerService.findById(customerID);
		Account account = accountService.save(new Account(customer.getCustomerId()));

		if(null != account){
			CreditBalanceRequest creditBalanceRequest = new CreditBalanceRequest(account.getCustomerID(), account.getAccountID(), initialCredit);
			Transaction transaction = transactionService.addCreadit(creditBalanceRequest);
			if(transaction.getTransactionType() == TransactionType.CREDIT){
				accountService.addToBalance(account.getAccountID(), transaction.getTransactionAmount());
				responseObject.put("message","New account is created for customer: " + customer.getName() + " " + customer.getSurname()+
						" and transaction is succesfull for the amount:" + transaction.getTransactionAmount());
			}else{
				responseObject.put("message","New account is created for customer: " + customer.getName() + " " + customer.getSurname());
			}
			responseObject.put("result",true);
			responseStatus = HttpStatus.CREATED;
		}else{
			responseObject.put("message","An error occured while creating a new account for customer: " + customer.getName() + " " + customer.getSurname());
		}

		return new ResponseEntity<>(responseObject, responseStatus);
	}

	@RequestMapping("/account/customer/{id}")
	public ResponseEntity<Object> getCustomer(@PathVariable("id") Long customerId){
		Map<String,Object> customerInfo = new HashMap<>();
		Customer customer = customerService.findById(customerId);
		List<Account> accounts = accountService.findAccountsByCustomerID(customerId);


		customerInfo.put("customerId",customer.getCustomerId());
		customerInfo.put("customerName",customer.getName());
		customerInfo.put("customerSurname",customer.getSurname());

		List<AccountStatement> accountBalances = accountService.getStatements(customer.getCustomerId());
		customerInfo.put("accounts",accountBalances);
		return new ResponseEntity<>(customerInfo, HttpStatus.OK);
	}
}
