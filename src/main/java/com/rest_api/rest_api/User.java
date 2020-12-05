package com.rest_api.rest_api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	
	private int amount;
	private String firstName;
	private String lastName;
	private String address;
	
	/*
	 * SETTERS
	 */
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String lastname) {
		this.lastName = lastname;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	/*
	 * GETTERS
	 */

	public int getAmount() {
		return this.amount;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getAddress() {
		return this.address;
	}
}
