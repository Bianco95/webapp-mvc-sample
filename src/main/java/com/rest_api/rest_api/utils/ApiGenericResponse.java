package com.rest_api.rest_api.utils;

public class ApiGenericResponse {

	String message;
	int code;
	
	public String getMessage() {
		return this.message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
}
