package com.bilge.cgaccountdemo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
	private @Id @GeneratedValue Long customerID;
	private String Name;
	private String Surname;

	public Customer() {}

	public Customer(String name, String surname) {
		Name = name;
		Surname = surname;
	}

	public Long getCustomerId() {
		return customerID;
	}

	public void setId(Long customerID) {
		this.customerID = customerID;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	@Override
	public String toString(){
		return "Customer{customerId="+this.customerID+", Name="+this.Name+", Surname="+this.Surname+"}";
	}
}
