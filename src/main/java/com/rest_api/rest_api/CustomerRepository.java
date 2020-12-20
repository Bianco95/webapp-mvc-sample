package com.rest_api.rest_api;

import java.util.ArrayList;
import java.util.List;

import com.rest_api.rest_api.utils.DbController;
import com.rest_api.rest_api.utils.Utils;

import java.sql.*;

/**
 * 
 * @author giulio
 */

public class CustomerRepository {

	private static CustomerRepository istance = null;

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
		String sql = "SELECT * from customers";
		List<Customer> customers = new ArrayList<Customer>();
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				customers.add(this.createCustomerFromDB(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("username"), rs.getInt("balance")));
			}
		} catch (Exception e) {
			System.out.println("Error occurred\n" + e.toString());
		}
		return customers;
	}

	public Customer getCustomerByCustomerID(int customerID) {
		String sql = "SELECT * from customers WHERE customerID=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, customerID);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				return this.createCustomerFromDB(rs.getInt("customerID"), rs.getString("firstName"),
						rs.getString("lastName"), rs.getString("username"), rs.getInt("balance"));
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public int getCustomerByUsername(String username) {
		String sql = "SELECT * from customers WHERE username=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet resultSet = st.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt("customerID");
			} else {
				return 1;
			}
		} catch (Exception e) {
			System.out.println(e);
			return 1;
		}
	}

	public int getCustomerByUsernameAndPassword(String username, String password) {
		String sql = "SELECT * from customers WHERE username=? AND password=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			System.out.println(e);
			return 1;
		}
	}

	public int createCustomer(Customer newCustomer) {
		String sql = "INSERT INTO customers VALUES (NULL,?, ?, ?, ?, ?)";
		Utils.checkDBConnection();
		int queryResult = 0;
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, newCustomer.getFirstName());
			st.setString(2, newCustomer.getLastName());
			st.setString(3, newCustomer.getUsername());
			st.setString(4, newCustomer.getPassword());
			st.setFloat(5, newCustomer.getBalance());
			queryResult = st.executeUpdate();
		} catch (Exception e) {
			return queryResult;
		}

		return queryResult;
	}

	public void updateCustomer(Customer customer) {
		String sql = "UPDATE customers set firstName=?, lastName=?, balance=? where customerID=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			System.out.println(customer.getFirstName());
			st.setString(1, customer.getFirstName());
			st.setString(2, customer.getLastName());
			st.setFloat(3, customer.getBalance());
			st.setInt(4, customer.getCustomerID());
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void deleteCustomer(int customerID) {
		String sql = "DELETE from customers where customerID=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, customerID);
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean isSuperAdmin(String username) {
		String sql = "SELECT * from superadmins WHERE username=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setString(1, username);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	private Customer createCustomerFromDB(int customerID, String firstName, String lastName, String username,
			float balance) {
		Customer newCustomer = new Customer();
		newCustomer.setCustomerID(customerID);
		newCustomer.setFirstName(firstName);
		newCustomer.setLastName(lastName);
		newCustomer.setUsername(username);
		newCustomer.setBalance(balance);
		return newCustomer;
	}

}