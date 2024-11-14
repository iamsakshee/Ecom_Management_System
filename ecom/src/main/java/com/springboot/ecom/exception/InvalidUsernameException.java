package com.springboot.ecom.exception;

public class InvalidUsernameException extends Exception{
    private String message;

    public InvalidUsernameException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
