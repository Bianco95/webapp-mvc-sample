package com.rest_api.rest_api;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author giulio
 * MOCK CLASS TO STORE DATA INSTEAD OF DB
 */

public class UserRepository {

	private static UserRepository istance = null;
	private List<User> users;

	/*
	 * CONSTRUCTOR
	 */

	public UserRepository() {
		this.users = new ArrayList<User>();

		User u1 = new User();
		u1.setAmount(10);
		u1.setFirstName("Giulio");
		u1.setLastName("Bianchini");
		u1.setAddress("test");

		User u2 = new User();
		u2.setAmount(10);
		u2.setFirstName("Andrea");
		u2.setLastName("Bianchini");
		u2.setAddress("test");

		this.users.add(u1);
		this.users.add(u2);
	}

	public static UserRepository getIstance() {
		if (istance == null)
			istance = new UserRepository();
		return istance;
	}

	public List<User> getUsers() {
		return this.users;
	}
}
