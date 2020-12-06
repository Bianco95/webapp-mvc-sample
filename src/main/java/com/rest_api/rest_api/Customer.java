package com.rest_api.rest_api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	private int customerID;
	private int balance;
	private String firstName;
	private String lastName;
	
	/*
	 * SETTERS
	 */
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setBalance(int amount) {
		this.balance = amount;
	}
	
	
	/*
	 * GETTERS
	 */
	public int getCustomerID() {
		return this.customerID;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
}
