package com.rest_api.rest_api;

import java.util.List;

public class ApiContentResponse<T> {

	String message;
	int code;
	int page;
	int pages;
	List<T> contents;
	
	/*
	 * GETTERS
	 */
	public String getMessage() {
		return this.message;
	}
	
	public int getCode() {
		return this.code;
	}
	
	public int getPage() {
		return this.page;
	}
	
	public int getPages() {
		return this.pages;
	}
	
	public List<T> getContents() {
		return this.contents;
	}
	
	/*
	 * SETTERS
	 */
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setPages(int pages) {
		this.pages = pages;
	}
	
	public void setContents(List<T> contents) {
		this.contents = contents;
	}
}
