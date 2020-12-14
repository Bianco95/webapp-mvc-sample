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

@Path("/transactions")
public class TransactionResource extends AbstractResource {
	
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
			response.setContentType("json/html");
			String username = request.getHeader("Authorization");
			boolean isSuperAdmin = false;
			System.out.println(username);
			if(username == null) {
				throw new ServletException("Auth header not provided");
			}
			isSuperAdmin = CustomerRepository.getIstance().isSuperAdmin(username);
			this.dataSanitizing(request);
			Integer gt = request.getParameter("gt") != null ? Integer.parseInt(request.getParameter("gt")) : null;
			Integer lt = request.getParameter("lt") != null ? Integer.parseInt(request.getParameter("lt")) : null;
			System.out.println(gt);
			System.out.println(lt);
			List<Transaction> transactions = ((TransactionRepository) this.repository).getTransactions(isSuperAdmin, username, gt, lt);
			this.sendAllContents(request, response, transactions);
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
			this.sendPostResponse(request, response);
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
			this.sendPutResponse(request, response);
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
			this.sendPutResponse(request, response);
		} catch (Exception err) {
			if(err.getMessage() == "not found") {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} else {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

}
