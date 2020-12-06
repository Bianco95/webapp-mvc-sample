package com.rest_api.rest_api.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbController {
	private static DbController istance = null;
	Connection conn = null;
	
	public DbController() {

		String dbName = "masterdb";
		String dbUserName = "root";
		String dbPassword = "password_secret";
		String connectionString = "jdbc:mysql://172.17.0.2/" + dbName + "?user=" + dbUserName + "&password="
				+ dbPassword + "&useUnicode=true&characterEncoding=UTF-8" + "&allowPublicKeyRetrieval=true"
				+ "&useSSL=false";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(connectionString);
		} catch (Exception e) {
			System.out.println("Error occurred \n" + e.toString());
		}
	}

	public static DbController getIstance() {
		if (istance == null)
			istance = new DbController();
		return istance;
	}
	
	public Connection getConnection() {
		return this.conn;
	}
}
