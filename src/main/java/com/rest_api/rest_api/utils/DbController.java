package com.rest_api.rest_api.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbController {
	private static DbController istance = null;
	Connection conn = null;
	private boolean isConnected = false;
	private String dbName;
	private String dbUserName;
	private String dbPassword;
	private String connectionString;
	
	public DbController() {

		this.dbName = "masterdb";
		this.dbUserName = "root";
		this.dbPassword = "password_secret";
		this.connectionString = "jdbc:mysql://172.17.0.2/" + dbName + "?user=" + dbUserName + "&password="
				+ dbPassword + "&useUnicode=true&characterEncoding=UTF-8" + "&allowPublicKeyRetrieval=true"
				+ "&useSSL=false";
		
		this.connect();
	}

	public static DbController getIstance() {
		if (istance == null)
			istance = new DbController();
		return istance;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
	
	public boolean getConnectionStatus() {
		return this.isConnected;
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connectionString);
			this.isConnected = true;
		} catch (Exception e) {
			this.isConnected = false;
			System.out.println("Error occurred \n" + e.toString());
		}
	}
}
