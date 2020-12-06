package com.rest_api.rest_api.utils;

import java.util.List;

public class ApiSingleResponse<T> {
	
	private Object content;
	int code;
	
	public Object getContent() {
		return this.content;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public void setObject(Object content) {
		this.content = content;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
}
