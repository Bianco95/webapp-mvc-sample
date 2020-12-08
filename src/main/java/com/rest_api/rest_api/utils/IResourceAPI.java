package com.rest_api.rest_api.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

public interface IResourceAPI<T> {
	
	public void findById(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
	
	public Response create(T object);
	
	public Response delete(int id);
	
	public Response update(T object);

}
