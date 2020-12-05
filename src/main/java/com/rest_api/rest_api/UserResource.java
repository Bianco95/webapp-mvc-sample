package com.rest_api.rest_api;

import java.util.List;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class UserResource {
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getUsers() {
		System.out.println("getUser called...");
		
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
		
		User u3 = new User();
		u3.setAmount(10);
		u3.setFirstName("Andrea");
		u3.setLastName("Bianchini");
		u3.setAddress("test");
		
		List<User> users = Arrays.asList(u1, u2, u3);

		return users;
	}
}
