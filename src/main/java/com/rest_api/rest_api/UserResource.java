package com.rest_api.rest_api;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rest_api.rest_api.utils.Utils;

@Path("/users")
public class UserResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<User> getUsers(
			@QueryParam("lastName") String lastName,
			@QueryParam("firstName") String firstName
			) {
		System.out.println("[getUsers] called...");

		List<User> users = UserRepository.getIstance().getUsers();
		List<User> userResult = new ArrayList<User>();

		if (lastName != null) {
			for (User user : users) {
				if (user.getLastName().equals(lastName)) {
					userResult.add(user);
				}
			}
		}
		
		if (firstName != null) {
			for (User user : users) {
				if (user.getFirstName().equals(firstName)) {
					userResult.add(user);
				}
			}
		}

		return Utils.intersection(users, userResult);
	}

	@GET
	@Path("user/{name}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public User getUserByName(@PathParam("name") String name) {
		return UserRepository.getIstance().getUserByName(name);
	}

	@POST
	@Path("user")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void createUser(User user) {
		UserRepository.getIstance().createUser(user);
	}
}
