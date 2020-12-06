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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.rest_api.rest_api.utils.IResourceAPI;
import com.rest_api.rest_api.utils.Utils;

@Path("/transactions")
public class TransactionResource implements IResourceAPI<Transaction> {

	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response findAll(@QueryParam("lastName") String lastName, @QueryParam("firstName") String firstName,
			@QueryParam("page") Integer page) {
		System.out.println("[getCustomers] called...");

		List<Transaction> customers = TransactionRepository.getIstance().getTransactions();
		try {
			int sizeOfPages = 1;
			int pages = Math.round(customers.size() / 25) == 0 ? 1 : Math.round(customers.size() / 25) + 1;
			if (customers.size() <= 25 * page) {
				sizeOfPages = customers.size();
			} else {
				sizeOfPages = 25 * page;
			}
			List<Transaction> paginatedCustomer = new ArrayList<Transaction>();
			try {
				paginatedCustomer = customers.subList(25 * (page - 1), sizeOfPages);
			} catch (Exception e) {
				return Response.ok(
						this.ow.writeValueAsString(
								Utils.ApiContentResponseBuilder("customers", 200, page, pages, paginatedCustomer)),
						MediaType.APPLICATION_JSON).build();
			}
			return Response.ok(
					this.ow.writeValueAsString(
							Utils.ApiContentResponseBuilder("customers", 200, page, pages, paginatedCustomer)),
					MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.serverError().entity("Internal error").build();
		}
	}

	@GET
	@Path("customer/{customerID}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response findById(@PathParam("customerID") int id) {
		System.out.println("[getCustomerByID] called...");
		try {
			Transaction transaction = TransactionRepository.getIstance().getTransactionByTransactionID(id);
			if(transaction == null) {
				throw new Error("unable to retrieve transaction");
			}
		return Response.ok(
				this.ow.writeValueAsString(
						Utils.ApiSingleResponseBuilder(200, transaction)),
				MediaType.APPLICATION_JSON).build();
		} catch(Exception err) {
			return Response.serverError().entity(err.getMessage()).build();
		}
	}

	@POST
	@Path("customer")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response create(Transaction object) {
		System.out.println("[createCustomer] Going to create new customer...");
		try {
			int result = TransactionRepository.getIstance().createCustomer(object);
			if(result != 1) {
				return Response.serverError().entity("unable to add transaction").build();
			}
			return Response.ok(this.ow.writeValueAsString(Utils.ApiResponseBuilder("transaction created", 201)),
					MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@PUT
	@Path("customer")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response update(Transaction object) {
		System.out.println("[updateCustomer] Going to update customer...");
		try {
			TransactionRepository.getIstance().updateTransaction(object);
			return Response.ok("customer updated", MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.serverError().entity("Internal error").build();
		}
	}

	@DELETE
	@Path("customer/{customerID}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response delete(@PathParam("customerID") int id) {
		System.out.println("[deleteCustomer] Going to delete customer...");
		try {
			Transaction transactionToDelete = TransactionRepository.getIstance().getTransactionByTransactionID(id);
			if (transactionToDelete == null) {
				throw new Error("Customer not found");
			}
			TransactionRepository.getIstance().deleteCustomer(id);
			return Response.ok(this.ow.writeValueAsString(Utils.ApiResponseBuilder("customer deleted", 200)),
					MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

}
