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

@Path("/transactions")
public class TransactionResource extends HttpServlet {

	private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	protected String resourceName = "";
	protected Object repository = new Object();
	//protected Object className = Transaction.class;
	
	public TransactionResource() {
		this.repository = TransactionRepository.getIstance();
		this.resourceName = "transaction";
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[get"+this.resourceName+"] called...");
		try {
			String username = request.getHeader("Authorization");
			boolean isSuperAdmin = false;
			System.out.println(username);
			if(username == null) {
				throw new ServletException("Auth header not provided");
			}
			isSuperAdmin = CustomerRepository.getIstance().isSuperAdmin(username);
			Integer page = 0;
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {
				page = 1;
			}
			response.setContentType("json/html");
			List<Transaction> transactions = ((TransactionRepository) this.repository).getTransactions(isSuperAdmin, username);
			if (page == 0) {
				response.getOutputStream().println(
						this.ow.writeValueAsString(Utils.ApiContentResponseBuilder(this.resourceName+"s", 200, 0, 0, transactions)));
			}
			int sizeOfPages = 1;
			int pages = Math.round(transactions.size() / 25) == 0 ? 1 : Math.round(transactions.size() / 25)+1;
			if (transactions.size() <= 25 * page) {
				sizeOfPages = transactions.size();
			} else {
				sizeOfPages = 25 * page;
			}
			List<Transaction> paginatedTransactions = new ArrayList<Transaction>();
			try {
				paginatedTransactions = transactions.subList(25 * (page - 1), sizeOfPages);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			response.getOutputStream().println(this.ow
					.writeValueAsString(Utils.ApiContentResponseBuilder("transactions", 200, page, pages, paginatedTransactions)));
		} catch (Exception err) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[createTransaction] Going to create new " + this.resourceName + "...");
		try {
			response.setContentType("json/html");
			Transaction transaction = new Gson().fromJson(request.getReader(), Transaction.class);
			int result = TransactionRepository.getIstance().createTransaction(transaction);
			if (result != 1) {
				throw new ServletException("not found");
			}
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("transaction created", 201)));
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public void doPut(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("[updateTransaction] Going to update transaction...");
		try {
			response.setContentType("json/html");
			Transaction transactionToUpdate = new Gson().fromJson(request.getReader(), Transaction.class);
			if (transactionToUpdate == null) {
				throw new ServletException("not found");
			}
			TransactionRepository.getIstance().updateTransaction(transactionToUpdate);
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("transaction updated", 201)));
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
		System.out.println("[deleteTransaction] Going to delete transaction...");
		try {
			response.setContentType("json/html");
			Integer id = Integer.parseInt(request.getParameter("id"));
			Transaction transactionToDelete = TransactionRepository.getIstance().getTransactionByTransactionID(id);
			if (transactionToDelete == null) {
				throw new ServletException("not found");
			}
			TransactionRepository.getIstance().deleteTransaction(id);
			response.getOutputStream()
					.println(this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder("transaction deleted", 200)));
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

}
