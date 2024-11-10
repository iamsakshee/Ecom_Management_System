package com.springboot.ecom.exception;

public class ResourceNotFoundException extends Exception {
	
	private String message;

	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
	
	

}
