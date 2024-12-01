package com.springboot.ecom.exception;

public class DuplicateEntryException extends Exception {
    private String message;

    public DuplicateEntryException(String message) {
        super();
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
