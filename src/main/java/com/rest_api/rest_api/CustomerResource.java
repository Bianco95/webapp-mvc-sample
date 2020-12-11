package com.rest_api.rest_api;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.google.gson.Gson;
import com.rest_api.rest_api.utils.Utils;

@Path("/customers")
public class CustomerResource extends HttpServlet {

	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	protected String resourceName = "";
	protected Object repository = new Object();

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
			Integer page = 0;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {
				page = 1;
			}
			response.setContentType("json/html");
			List<Customer> customers = ((CustomerRepository) this.repository).getCustomers();
			if (page == 0) {
				response.getOutputStream().println(this.ow.writeValueAsString(
						Utils.ApiContentResponseBuilder(this.resourceName + "s", 200, 0, 0, customers)));
			}
			int sizeOfPages = 1;
			int pages = Math.round(customers.size() / 25) == 0 ? 1 : Math.round(customers.size() / 25) + 1;
			if (customers.size() <= 25 * page) {
				sizeOfPages = customers.size();
			} else {
				sizeOfPages = 25 * page;
			}
			List<Customer> paginatedCustomers = new ArrayList<Customer>();
			try {
				paginatedCustomers = customers.subList(25 * (page - 1), sizeOfPages);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			response.getOutputStream().println(this.ow.writeValueAsString(
					Utils.ApiContentResponseBuilder("customers", 200, page, pages, paginatedCustomers)));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void doGetById(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[getCustomerByID] called...");
		try {
			response.setContentType("json/html");
			Integer id = Integer.parseInt(request.getParameter("id"));
			Customer customer = CustomerRepository.getIstance().getCustomerByCustomerID(id);
			if (customer == null) {
				throw new ServletException("not found");
			}
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiSingleResponseBuilder(200, customer)));
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			
		}
	}

	@SuppressWarnings("deprecation")
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[createCustomer] Going to create new customer...");
		try {
			response.setContentType("json/html");
			Customer customer = new Gson().fromJson(request.getReader(), Customer.class);
						
			if(CustomerRepository.getIstance().getCustomerByUsername(customer.getUsername()) != 1) {
				throw new ServletException("Username already in use");
			}
			
			int result = CustomerRepository.getIstance().createCustomer(customer);
			if (result != 1) {
				throw new ServletException("Error during create of customer");
			}
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("customer created", 201)));
		} catch (Exception err) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, err.getMessage());
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
			Customer customerToUpdate = new Gson().fromJson(request.getReader(), Customer.class);
			if (customerToUpdate == null) {
				throw new ServletException("not found");
			}
			CustomerRepository.getIstance().updateCustomer(customerToUpdate);
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("customer updated", 201)));
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
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
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("customer deleted", 200)));
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

}
