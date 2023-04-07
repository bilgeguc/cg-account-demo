package com.bilge.cgaccountdemo.configs;

public enum TransactionType {
	CREDIT(1),
	WITHDRAW(2),
	ERROR(3),
	ZERO(4);

	private final int value;

	TransactionType(int value){this.value = value;}

	public int getValue(){return value;}
}
