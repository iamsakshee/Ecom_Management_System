package com.springboot.ecom.exception;

public class DuplicateUserIdException extends Exception {
	
	private String message;

	public DuplicateUserIdException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	

}
