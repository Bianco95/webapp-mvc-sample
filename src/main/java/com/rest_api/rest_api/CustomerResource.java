package com.rest_api.rest_api;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rest_api.rest_api.utils.Utils;

@Path("customers")
public class CustomerResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Customer> getCustomers(
			@QueryParam("lastName") String lastName,
			@QueryParam("firstName") String firstName
			) {
		System.out.println("[getCustomers] called...");
		
		List<Customer> users = CustomerRepository.getIstance().getCustomers();
		return Utils.intersection(users, users);
	}

	@GET
	@Path("customer/{customerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Customer getCustomerByID(@PathParam("customerID") int customerID) {
		System.out.println("[getCustomerByID] called...");
		return CustomerRepository.getIstance().getCustomerByCustomerID(customerID);
	}

	@POST
	@Path("customer")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void createCustomer(Customer newCustomer) {
		System.out.println("[createCustomer] Going to create new customer...");
		CustomerRepository.getIstance().createCustomer(newCustomer);
	}
	
	@PUT
	@Path("customer")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void updateCustomer(Customer newCustomer) {
		System.out.println("[updateCustomer] Going to update customer...");
		CustomerRepository.getIstance().updateCustomer(newCustomer);
	}
	
	@DELETE
	@Path("customer/{customerID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void deleteCustomer(@PathParam("customerID") int customerID) {
		System.out.println("[deleteCustomer] Going to delete customer...");
		CustomerRepository.getIstance().deleteCustomer(customerID);
	}
}
