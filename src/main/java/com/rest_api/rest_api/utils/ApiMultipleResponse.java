package com.rest_api.rest_api.utils;

import java.util.List;

public class ApiMultipleResponse<T> {

	private String message;
	private int code;
	private int page;
	private int pages;
	private List<T> contents;
	
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
