package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.service.CustomerService;

@RestController
public class CustomerController {
	
	@Autowired
	
	private CustomerService customerService;
	
	@PostMapping("/customer/add")
	public Customer insertCustomer(@RequestBody Customer customer)
	{
		return customerService.insertCustomer(customer);
	}
	
	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id, ResponseMessageDto dto)
	{
		
		try {
			customerService.validate(id);
			customerService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		dto.setMsg("Customer deleted!");
		return ResponseEntity.ok(dto);
		
	}
	
	
}

















