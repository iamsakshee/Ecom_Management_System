package com.springboot.ecom.exception;

import com.springboot.ecom.dto.ResponseMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResponseMessageDto dto;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseMessageDto> handleResourceNotFoundException(ResourceNotFoundException e) {
        dto.setMsg(e.getMessage());
        return ResponseEntity.status(404).body(dto);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ResponseMessageDto> handleInvalidUsernameException(InvalidUsernameException e) {
        dto.setMsg(e.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(DuplicateEntryException.class)
    public ResponseEntity<ResponseMessageDto> handleDuplicateEntryException(DuplicateEntryException e) {
        dto.setMsg(e.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseMessageDto> handleGeneralException(Exception e) {
        dto.setMsg("An error occurred: " + e.getMessage());
        return ResponseEntity.badRequest().body(dto);
    }
}
