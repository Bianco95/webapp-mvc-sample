package com.rest_api.rest_api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
	
	private int customerID;
	private float balance;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	
	/*
	 * SETTERS
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setBalance(float amount) {
		this.balance = amount;
	}
	
	
	/*
	 * GETTERS
	 */
	
	public int getCustomerID() {
		return this.customerID;
	}
	
	public float getBalance() {
		return this.balance;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
}
