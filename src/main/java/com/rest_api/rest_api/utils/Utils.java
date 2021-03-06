package com.rest_api.rest_api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class Utils {

	public static void checkDBConnection() {
		if(DbController.getIstance().getConnectionStatus() == false) {
			DbController.getIstance().connect();
			throw new Error("unable to connect to database");
		}
	}
	
	public static <T> ApiMultipleResponse<T> ApiContentResponseBuilder(String message, int code, Integer page, Integer pages, List<T> contents) {
		ApiMultipleResponse<T> apiResponse = new ApiMultipleResponse<T>();
		apiResponse.setMessage(message);
		apiResponse.setCode(code);
		if(contents != null) {
			apiResponse.setContents(contents);
		}
		if(page != null) {
			apiResponse.setPage(page);
		}
		if(pages != null) {
			apiResponse.setPages(pages);
		}
		return apiResponse;
	}
	
	public static ApiGenericResponse ApiGenericResponseBuilder(String message, int code) {
		ApiGenericResponse apiResponse = new ApiGenericResponse();
		apiResponse.setMessage(message);
		apiResponse.setCode(code);
		return apiResponse;
	}
	
	public static ApiSingleResponse<Object> ApiSingleResponseBuilder(int code, Object object) {
		ApiSingleResponse<Object> apiResponse = new ApiSingleResponse<Object>();
		apiResponse.setCode(code);
		apiResponse.setObject(object);
		return apiResponse;
	}
	
	public static String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	        InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }

	    body = stringBuilder.toString();
	    return body;
	}
	
}
