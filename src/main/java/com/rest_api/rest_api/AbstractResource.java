package com.rest_api.rest_api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.rest_api.rest_api.utils.Utils;

public abstract class AbstractResource extends HttpServlet {

	protected ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
	protected String resourceName = "";
	protected Object repository = new Object();

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public abstract void doGet(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public abstract void doPost(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException;

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public abstract void doPut(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException;

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public abstract void doDelete(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException;

	public <T> void sendAllContents(@Context HttpServletRequest request, @Context HttpServletResponse response,
			List<T> contents) throws IOException, ServletException {
		Integer page = 0;
		try {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (Exception e) {
				page = 1;
			}

			if (page == 0) {
				response.getOutputStream().println(this.ow.writeValueAsString(
						Utils.ApiContentResponseBuilder(this.resourceName + "s", 200, 0, 0, contents)));
			}

			int sizeOfPages = 1;
			int pages = Math.round(contents.size() / 25) == 0 ? 1 : Math.round(contents.size() / 25) + 1;
			if (contents.size() <= 25 * page) {
				sizeOfPages = contents.size();
			} else {
				sizeOfPages = 25 * page;
			}
			List<T> paginatedCustomers = new ArrayList<T>();
			try {
				paginatedCustomers = contents.subList(25 * (page - 1), sizeOfPages);
			} catch (Exception e) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			response.getOutputStream().println(this.ow.writeValueAsString(
					Utils.ApiContentResponseBuilder(this.resourceName + "s", 200, page, pages, paginatedCustomers)));
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	public void dataSanitizing(@Context HttpServletRequest request) throws IOException {
		
		Enumeration<String> parameterNames = request.getParameterNames();
		
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (int i = 0; i < paramValues.length; i++) {
                String paramValue = paramValues[i];
                if (!paramValue.matches("[\\w*\\s*]*")) {
    				throw new Error("input param not valid");
    			}
            }
        }
	}

	public <T> void sendPostResponse(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {

		response.getOutputStream().println(
				this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder(this.resourceName + " created", 201)));
	}

	public <T> void sendPutResponse(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {

		response.getOutputStream().println(
				this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder(this.resourceName + " updated", 201)));
	}

	public <T> void sendDeleteResponse(@Context HttpServletRequest request, @Context HttpServletResponse response)
			throws IOException, ServletException {

		response.getOutputStream().println(
				this.ow.writeValueAsString(Utils.ApiGenericResponseBuilder(this.resourceName + " deleted", 200)));
	}
}
