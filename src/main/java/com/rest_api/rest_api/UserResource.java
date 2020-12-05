package com.rest_api.rest_api;

import java.util.List;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/users")
public class UserResource {
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<User> getUsers() {
		System.out.println("[getUsers] called...");
		return UserRepository.getIstance().getUsers();
	}
	
	@POST
	@Path("user")
	@Consumes(MediaType.APPLICATION_XML)
	public void createUser(User user) {
		UserRepository.getIstance().createUser(user);
	}
	
}
