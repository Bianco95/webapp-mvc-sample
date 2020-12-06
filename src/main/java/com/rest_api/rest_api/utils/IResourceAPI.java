package com.rest_api.rest_api.utils;

import javax.ws.rs.core.Response;

public interface IResourceAPI<T> {
	
	public Response findById(int id);
	
	public Response create(T object);
	
	public Response delete(int id);
	
	public Response update(T object);

}
