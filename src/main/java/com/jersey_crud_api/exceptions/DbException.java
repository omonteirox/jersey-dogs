package com.jersey_crud_api.exceptions;

public class DbException extends Exception {

	
	private static final long serialVersionUID = 1L;
	
	public DbException(String message, Throwable cause) {
        super(message, cause);
    }
	public DbException(String message) {
        super(message);
    }
}
