package com.rest_api.rest_api;

import java.util.ArrayList;
import java.util.List;

import com.rest_api.rest_api.utils.DbController;
import com.rest_api.rest_api.utils.Utils;

import java.sql.*;
import java.util.Date;

/**
 * 
 * @author giulio
 */

public class TransactionRepository {

	private static TransactionRepository istance = null;

	public static TransactionRepository getIstance() {
		if (istance == null)
			istance = new TransactionRepository();
		return istance;
	}

	/**
	 * 
	 * METHOD TO QUERY THE DB
	 */

	public List<Transaction> getTransactions(Boolean isSuperAdmin, String username, Integer gt, Integer lt) {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			String sql = "";
			PreparedStatement st = null;
			Utils.checkDBConnection();
			int customerID = CustomerRepository.getIstance().getCustomerByUsername(username);			
			if (isSuperAdmin) {
				sql = "SELECT * from transactions";
				if(gt != null && lt == null) {
					sql += " WHERE amount > ?";
				} else if(gt == null && lt != null) {
					sql += " WHERE amount < ?";
				}else if(gt != null && lt != null){
					sql += " WHERE amount BETWEEN ? AND ?";
				}
				
			} else {
				sql = "SELECT * from transactions WHERE customerID =?";
				if(gt != null && lt == null) {
					sql += " AND amount > ?";
				} else if(gt == null && lt != null) {
					sql += " AND amount < ?";
				}else if(gt != null && lt != null) {
					sql += " AND amount BETWEEN ? AND ?";
				}
			}
			
			st = DbController.getIstance().getConnection().prepareStatement(sql);
			
			if(!isSuperAdmin) {
				st.setInt(1, customerID);
				if( (gt != null && lt == null) || (gt == null && lt != null)) {
					st.setInt(2, gt);
				} if(gt != null && lt != null) {
					st.setInt(2, gt);
					st.setInt(3, lt);
				}
			} else {
				if( (gt != null && lt == null) || (gt == null && lt != null)) {
					st.setInt(1, gt);
				}else if(gt != null && lt != null) {
					st.setInt(1, gt);
					st.setInt(2, lt);
				}
			}
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				transactions.add(this.createTransactionFromDB(rs.getInt("transactionID"), rs.getInt("customerID"),
						rs.getInt("amount"), rs.getString("purchase_date")));
			}
		} catch (Exception e) {
			System.out.println("Error occurred\n" + e.toString());
		}
		return transactions;
	}

	public Transaction getTransactionByTransactionID(int transactionID) {
		String sql = "SELECT * from transactions WHERE transactionID=" + transactionID;
		Utils.checkDBConnection();
		try {
			Statement st = DbController.getIstance().getConnection().createStatement();
			ResultSet rs = st.executeQuery(sql);

			if (rs.next()) {
				return this.createTransactionFromDB(rs.getInt("transactionID"), rs.getInt("customerID"),
						rs.getInt("amount"), rs.getString("purchase_date"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}

	public int createTransaction(Transaction newTrasaction) {
		String sql = "INSERT INTO transactions VALUES (NULL,?,?,?)";
		Utils.checkDBConnection();
		int queryResult = 0;
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, newTrasaction.getCustomerID());
			st.setInt(2, newTrasaction.getAmount());
			st.setTimestamp(3, new Timestamp(new Date().getTime()));
			queryResult = st.executeUpdate();
		} catch (Exception e) {
			System.out.println("e" + e.toString());
			return queryResult;
		}

		return queryResult;
	}

	public void updateTransaction(Transaction transaction) {
		String sql = "UPDATE transactions set amount=? where transactionID=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, transaction.getAmount());
			st.setInt(2, transaction.getTransactionID());
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void deleteTransaction(int transactionID) {
		String sql = "DELETE from transactions where transactionID=?";
		Utils.checkDBConnection();
		try {
			PreparedStatement st = DbController.getIstance().getConnection().prepareStatement(sql);
			st.setInt(1, transactionID);
			st.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private Transaction createTransactionFromDB(int transactionID, int customerID, int amount, String purchase_date) {
		Transaction newTransaction = new Transaction();
		newTransaction.setPurchaseDate(purchase_date);
		newTransaction.setAmount(amount);
		newTransaction.setCustomerID(customerID);
		newTransaction.setTransactionID(transactionID);
		return newTransaction;
	}

}