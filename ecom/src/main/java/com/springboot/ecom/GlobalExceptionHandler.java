package com.springboot.ecom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.service.CustomerService;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@Autowired
	private ResponseMessageDto dto;
	
	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
	ResponseEntity<?> handleResourceNotFoundException(Exception e){
		 dto.setMsg(e.getMessage());
		 return ResponseEntity.badRequest().body(dto);
	}


}
