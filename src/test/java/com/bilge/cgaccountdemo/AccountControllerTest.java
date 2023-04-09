package com.bilge.cgaccountdemo;

import com.bilge.cgaccountdemo.configs.TransactionType;
import com.bilge.cgaccountdemo.controller.api.AccountController;
import com.bilge.cgaccountdemo.controller.request.CreditBalanceRequest;
import com.bilge.cgaccountdemo.entities.Account;
import com.bilge.cgaccountdemo.entities.Customer;
import com.bilge.cgaccountdemo.entities.Transaction;
import com.bilge.cgaccountdemo.errors.CustomerNotFoundException;
import com.bilge.cgaccountdemo.model.ResponseObject;
import com.bilge.cgaccountdemo.repo.CustomerRepository;
import com.bilge.cgaccountdemo.service.AccountService;
import com.bilge.cgaccountdemo.service.AccountServiceImpl;
import com.bilge.cgaccountdemo.service.CustomerService;
import com.bilge.cgaccountdemo.service.CustomerServiceImpl;
import com.bilge.cgaccountdemo.service.TransactionService;
import com.bilge.cgaccountdemo.service.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.JsonPathResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = AccountController.class)
public class AccountControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	CustomerServiceImpl customerServiceMock;

	@MockBean
	AccountServiceImpl accountServiceMock;

	@MockBean
	TransactionServiceImpl transactionServiceMock;

	@BeforeEach
	void setUp(){
		Customer customer1 = new Customer("Bilge","Güç");customer1.setId(new Long(1));
		Customer customer2 = new Customer("Alexander","Wielemaker");customer2.setId(new Long(2));
		Customer customer3 = new Customer("Özlem Işıl","Güç");customer3.setId(new Long(3));
		Customer customer4 = new Customer("Tante","The Cat");customer4.setId(new Long(4));
		Mockito.when(this.customerServiceMock.findById(new Long(1))).thenReturn(customer1);
		Mockito.when(this.customerServiceMock.findById(new Long(2))).thenReturn(customer2);
		Mockito.when(this.customerServiceMock.findById(new Long(3))).thenReturn(customer3);
		Mockito.when(this.customerServiceMock.findById(new Long(4))).thenReturn(customer4);
		Mockito.when(this.customerServiceMock.findById(new Long(55))).thenThrow(new CustomerNotFoundException(new Long(55)));

		Account account1 = new Account(customer1.getCustomerId());account1.setAccountID(new Long(5));
		Mockito.when(this.accountServiceMock.save(any(Account.class))).thenReturn(account1);

		Mockito.when(this.transactionServiceMock.addCreadit(any(CreditBalanceRequest.class))).thenCallRealMethod();
	}

	@Test
	public void createNewAccountWithInitialCredit() throws Exception {
		Customer customer = new Customer("Bilge","Güç");customer.setId(new Long(1));
		BigDecimal initialCredit = BigDecimal.valueOf(12.14);
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("result",true);
		responseObject.put("message","New account is created for customer: " + customer.getName() + " " + customer.getSurname()+
				" and transaction is succesfull for the amount:" + initialCredit);
		createNewAccount(customer,initialCredit,responseObject, MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void createNewAccountWithZeroCredit() throws Exception {
		Customer customer = new Customer("Bilge","Güç");customer.setId(new Long(1));
		BigDecimal initialCredit = BigDecimal.ZERO;
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("result",true);
		responseObject.put("message","New account is created for customer: " + customer.getName() + " " + customer.getSurname());
		createNewAccount(customer, initialCredit,responseObject, MockMvcResultMatchers.status().isCreated());
	}

	@Test
	public void getCustomerStatement() throws Exception{
		Customer customer = new Customer("Bilge","Güç");customer.setId(new Long(1));
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("customerName",customer.getName());
		responseObject.put("customerSurname",customer.getSurname());
		responseObject.put("customerId",customer.getCustomerId());
		responseObject.put("accounts",new ArrayList<>());
		getCustomer(customer, responseObject, MockMvcResultMatchers.status().isOk(),new Gson().toJson(responseObject));
	}

	@Test
	public void createNewAccountWithWrongCustomerID() throws Exception{
		Customer customer = new Customer("Serbet","The Cat");customer.setId(new Long(55));
		BigDecimal initialCredit = BigDecimal.ZERO;
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("result",false);
		responseObject.put("message","Can't find the cutomer : " + customer.getCustomerId());
		createNewAccount(customer,initialCredit, responseObject, MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void getCustomerStatementWithoutAccounts() throws Exception{
		Customer customer = new Customer("Alexander","Wielemaker");customer.setId(new Long(2));
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("customerName",customer.getName());
		responseObject.put("customerSurname",customer.getSurname());
		responseObject.put("customerId",customer.getCustomerId());
		responseObject.put("accounts",new ArrayList<>());
		getCustomer(customer, responseObject, MockMvcResultMatchers.status().isOk(),new Gson().toJson(responseObject));
	}

	@Test
	public void getCustomerStatementWithWrongCustomer() throws Exception{
		Customer customer = new Customer("Serbet","The Cat");customer.setId(new Long(55));
		Map<String,Object> responseObject = new HashMap<>();
		responseObject.put("result",false);
		responseObject.put("message","Can't find the cutomer : " + customer.getCustomerId());
		getCustomer(customer, responseObject, MockMvcResultMatchers.status().isNotFound(),new Gson().toJson(responseObject));
	}
	public void createNewAccount(Customer customer, BigDecimal initialCredit, Map<String,Object> responseObject, ResultMatcher resultMatcher) throws Exception {
		this.mockMvc.perform(
				MockMvcRequestBuilders
						.post("/account/new")
						.param("customerID",String.valueOf(customer.getCustomerId()))
						.param("initialCredit",String.valueOf(initialCredit)))
				.andExpect(resultMatcher)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(jsonPath("$.result").value(responseObject.get("result")))
				.andExpect(jsonPath("$.message").value(responseObject.get("message")))
				.andDo(MockMvcResultHandlers.print());
	}

	public void getCustomer(Customer customer, Map<String,Object> responseObject, ResultMatcher resultMatcher, String jsonResponse) throws Exception{
		this.mockMvc.perform(
						MockMvcRequestBuilders
								.get("/account/customer/"+customer.getCustomerId()))
				.andExpect(resultMatcher)
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().json(jsonResponse))
				.andDo(MockMvcResultHandlers.print());
	}
}
