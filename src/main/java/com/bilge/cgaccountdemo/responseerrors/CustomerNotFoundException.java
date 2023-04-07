package com.bilge.cgaccountdemo.responseerrors;

public class CustomerNotFoundException extends RuntimeException{
	public CustomerNotFoundException(Long customerID){
		super("Can't find the cutomer : " + customerID);
	}
}
