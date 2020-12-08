package com.rest_api.rest_api;

import java.sql.Timestamp;

/**
 * @author giulio
 *
 */

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
	
	private int transactionID;
	private int customerID;
	private int amount;
	private String purchase_date;
	
	/*
	 * SETTERS
	 */
	
	public int getTransactionID() {
		return this.transactionID;
	}
	
	public int getCustomerID() {
		return this.customerID;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public String getPurchaseDate() {
		return this.purchase_date;
	}
	
	/*
	 * GETTERS
	 */
	
	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void setPurchaseDate(String purchase_date) {
		this.purchase_date = purchase_date;
	}
	
}
