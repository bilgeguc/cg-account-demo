package com.bilge.cgaccountdemo.errors;

public class AccountNotFoundException extends RuntimeException{
	public AccountNotFoundException(Long accountID){ super("Can't find the account : " + accountID);}
}
