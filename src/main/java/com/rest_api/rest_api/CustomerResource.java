package com.rest_api.rest_api;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("/customers")
public class CustomerResource extends AbstractResource {

	public CustomerResource() {
		this.repository = CustomerRepository.getIstance();
		this.resourceName = "customer";
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[get" + this.resourceName + "] called...");
		try {
			response.setContentType("json/html");
			Integer id = null;
			try {
				id = Integer.parseInt(request.getParameter("id"));
			} catch (Exception e) {
				System.out.println("id not provided");
			}
			if (id != null) {
				Customer customer = ((CustomerRepository) this.repository).getCustomerByCustomerID(id);
				if (customer == null) {
					throw new ServletException("not found");
				}
				try {
					response.addHeader("Etag", String.valueOf(customer.computeEtag()));
				} catch (Exception e) {
					System.out.println(e);
				}
				this.sendSingleContent(request, response, customer);
				return;
			}
			List<Customer> customers = ((CustomerRepository) this.repository).getCustomers();
			this.sendAllContents(request, response, customers);
		} catch (Exception err) {
			this.handleError(response, err);
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[createCustomer] Going to create new " + this.resourceName + "...");
		try {
			response.setContentType("json/html");
			Customer customer = new Gson().fromJson(request.getReader(), Customer.class);

			System.out.println(customer.getUsername());

			if (CustomerRepository.getIstance().getCustomerByUsername(customer.getUsername()) != 1) {
				throw new ServletException("Username already in use");
			}

			int result = CustomerRepository.getIstance().createCustomer(customer);
			if (result != 1) {
				throw new ServletException("Error during create of " + this.resourceName);
			}
			this.sendPostResponse(request, response);
		} catch (Exception err) {
			this.handleError(response, err);
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doPut(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[updateCustomer] Going to update customer...");
		try {
			response.setContentType("json/html");
			Integer etag = Integer.parseInt(request.getHeader("Etag"));

			Customer customerToUpdate = new Gson().fromJson(request.getReader(), Customer.class);
			if (customerToUpdate == null) {
				throw new ServletException("not found");
			}

			Customer customer = ((CustomerRepository) this.repository)
					.getCustomerByCustomerID(customerToUpdate.getCustomerID());

			if (customer == null) {
				throw new ServletException("not found");
			}
			
			if (customer.computeEtag() != etag) {
				throw new ServletException("Etag does not match");
			}

			CustomerRepository.getIstance().updateCustomer(customerToUpdate);
			this.sendPutResponse(request, response);
		} catch (Exception err) {
			this.handleError(response, err);
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doDelete(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[deleteCustomer] Going to delete customer...");
		try {
			response.setContentType("json/html");
			Integer id = Integer.parseInt(request.getParameter("id"));
			Customer customerToDelete = CustomerRepository.getIstance().getCustomerByCustomerID(id);
			if (customerToDelete == null) {
				throw new ServletException("not found");
			}
			CustomerRepository.getIstance().deleteCustomer(id);
			this.sendDeleteResponse(request, response);
		} catch (Exception err) {
			this.handleError(response, err);
		}
	}

}
