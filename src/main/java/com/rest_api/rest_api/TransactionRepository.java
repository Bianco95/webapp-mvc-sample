package com.rest_api.rest_api;

import java.util.ArrayList;
import java.util.List;

import com.rest_api.rest_api.utils.DbController;

import java.sql.*;

/**
 * 
 * @author giulio
 */

public class TransactionRepository {

	private static TransactionRepository istance = null;
	private List<Transaction> transactions;
	
	public static TransactionRepository getIstance() {
		if (istance == null)
			istance = new TransactionRepository();
		return istance;
	}

	/**
	 * 
	 * METHOD TO QUERY THE DB
	 */

	public List<Transaction> getTransactions() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String sql = "SELECT * from transactions";
		try {
			Statement st = DbController.getIstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				transactions.add(this.createTransactionFromDB(rs.getInt("transactionID"), rs.getInt("customerID"),
						rs.getInt("amount"), rs.getTimestamp("purchase_date")));
			}
		} catch (Exception e) {
			System.out.println("Error occurred\n" + e.toString());
		}
		return transactions;
	}

	public Transaction getTransactionByTransactionID(int transactionID) {
		String sql = "SELECT * from transactions WHERE transactionID=" + transactionID;
		try {
			Statement st = DbController.getIstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				return this.createTransactionFromDB(rs.getInt("transactionID"), rs.getInt("customerID"),
						rs.getInt("amount"), rs.getTimestamp("purchase_date"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public int createCustomer(Transaction newTrasaction) {
		String sql = "INSERT INTO transactions VALUES (NULL,?,?,?)";
		int queryResult = 0;
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, newTrasaction.getCustomerID());
			st.setInt(2, newTrasaction.getAmount());
			st.setTimestamp(3, newTrasaction.getPurchaseDate());
			queryResult = st.executeUpdate();
		} catch (Exception e) {
			return queryResult;
		}

		return queryResult;
	}
s
	public void updateTransaction(Transaction transaction) {
		String sql = "UPDATE transactions set firstName=?, lastName=?, balance=? where customerID=?";
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			// WIP
			/*st.setString(1, customer.getFirstName());
			st.setString(2, customer.getLastName());
			st.setInt(3, customer.getBalance());
			st.setInt(4, customer.getCustomerID());*/
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
	
	
	private Transaction createTransactionFromDB(int transactionID, int customerID, int amount, Timestamp purchase_date) {
		Transaction newTransaction = new Transaction();
		newTransaction.setPurchaseDate(purchase_date);
		newTransaction.setAmount(amount);
		newTransaction.setCustomerID(customerID);
		newTransaction.setTransactionID(transactionID);
		return newTransaction;
	}

}