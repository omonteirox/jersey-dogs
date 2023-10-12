package com.jersey_crud_api.models;

import java.util.ArrayList;
import java.util.List;

public class ModelResponse<T> {
	 private List<String> errors = new ArrayList<String>();
	    private T data;

	    public ModelResponse(List<String> errors, T data) {
	        this.errors = errors;
	        this.data = data;
	    }
	    public ModelResponse(T data) {
	    	this.data = data;
	    	
	    }
	    public ModelResponse(String error) {
	    	errors.add(error);
	    }
	    
	    public List<String> getErrors() {
	        return errors;
	    }

	    public void setErrors(List<String> errors) {
	        this.errors = errors;
	    }

	    public T getData() {
	        return data;
	    }

	    public void setData(T data) {
	        this.data = data;
	    }
	   
}
