package com.rest_api.rest_api.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.rest_api.rest_api.ApiContentResponse;
import com.rest_api.rest_api.ApiResponse;

public class Utils {

	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
	
	public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

	public static <T> ApiContentResponse<T> ApiContentResponseBuilder(String message, int code, Integer page, Integer pages, List<T> contents) {
		ApiContentResponse<T> apiResponse = new ApiContentResponse<T>();
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
	
	public static ApiResponse ApiResponseBuilder(String message, int code) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage(message);
		apiResponse.setCode(code);
		return apiResponse;
	}
	
}
