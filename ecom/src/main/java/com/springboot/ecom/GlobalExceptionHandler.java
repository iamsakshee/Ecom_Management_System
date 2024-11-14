package com.springboot.ecom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private ResponseMessageDto dto;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<?> handleResourceNotFoundException(Exception e){
		 dto.setMsg(e.getMessage());
		 return ResponseEntity.badRequest().body(dto);
	}


}