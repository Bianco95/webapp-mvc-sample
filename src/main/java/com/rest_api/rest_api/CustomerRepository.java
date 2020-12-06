package com.rest_api.rest_api;

import java.util.ArrayList;
import java.util.List;

import com.rest_api.rest_api.utils.DbController;

import java.sql.*;

/**
 * 
 * @author giulio
 */

public class CustomerRepository {

	private static CustomerRepository istance = null;
	private List<Customer> customers;

	public static CustomerRepository getIstance() {
		if (istance == null)
			istance = new CustomerRepository();
		return istance;
	}

	/**
	 * 
	 * METHOD TO QUERY THE DB
	 */

	public List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		String sql = "SELECT * from customers";
		try {
			Statement st = DbController.getIstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			// iterate over the returned values
			while (rs.next()) {
				customers.add(this.createCustomerFromDB(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getInt("balance")));
			}
		} catch (Exception e) {
			System.out.println("Error occurred\n" + e.toString());
		}
		return customers;
	}

	public Customer getCustomerByCustomerID(int customerID) {
		String sql = "SELECT * from customers WHERE customerID=" + customerID;
		try {
			Statement st = DbController.getIstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				return this.createCustomerFromDB(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getInt("balance"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public int createCustomer(Customer newCustomer) {
		String sql = "INSERT INTO customers VALUES (NULL,?,?,?)";
		int queryResult = 0;
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, newCustomer.getFirstName());
			st.setString(2, newCustomer.getLastName());
			st.setInt(3, newCustomer.getBalance());
			queryResult = st.executeUpdate();
		} catch (Exception e) {
			return queryResult;
		}

		return queryResult;
	}

	public void updateCustomer(Customer customer) {
		String sql = "UPDATE customers set firstName=?, lastName=?, balance=? where customerID=?";
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, customer.getFirstName());
			st.setString(2, customer.getLastName());
			st.setInt(3, customer.getBalance());
			st.setInt(4, customer.getCustomerID());
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public void deleteCustomer(int customerID) {
		String sql = "DELETE from customers where customerID=?";
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, customerID);
			st.executeUpdate();
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	private Customer createCustomerFromDB(int customerID, String firstName, String lastName, int balance) {
		Customer newCustomer = new Customer();
		newCustomer.setCustomerID(customerID);
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setBalance(balance);
		return newCustomer;
	}

	/**
	 * 
	 * DEPRECATED METHOD WITH NO SQL INTEGRATION
	 */

	public List<Customer> getCustomers_nosql() {
		return this.customers;
	}

	public Customer getCustomerByName_nosql(String firstName) {
		for (Customer user : this.customers) {
			if (user.getFirstName().equals(firstName)) {
				return user;
			}
		}
		return null;
	}

	public Customer getCustomerByLastName_nosql(String lastName) {
		for (Customer user : this.customers) {
			if (user.getLastName().equals(lastName)) {
				return user;
			}
		}
		return null;
	}

	public void createCustomer_nosql(Customer customer) {
		this.customers.add(customer);
	}

}