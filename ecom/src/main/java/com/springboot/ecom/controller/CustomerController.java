package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.DuplicateUserIdException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.UserService;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/customer/add/{uid}")
	public ResponseEntity<?> insertCustomer(@PathVariable int uid, @RequestBody Customer customer, ResponseMessageDto dto)
	{
		
		User user = null;
		try {
			user = userService.validate(uid);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
			}
		
		customer.setUser(user);
		try {
			customerService.insertCustomer(customer);
		} catch (DuplicateUserIdException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		dto.setMsg("Customer added successfully.");
	    return ResponseEntity.ok(dto);
		
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
	
	@GetMapping("/customer/zipcode/get")
	public ResponseEntity<?> getCustomerByZipcode(@RequestParam String code)
	{
		try {
		Integer zipcode = Integer.parseInt(code);
		
		List<Customer> list = customerService.getCustomerByZipcode(zipcode);
		return ResponseEntity.ok(list);
		}
		catch(NumberFormatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
	}

}
	
	
	@PutMapping("/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer newCustomer, ResponseMessageDto dto) {
	    try {
	        Customer existingCustomer = customerService.validate(id);

	        if (newCustomer.getName() != null) 
	        	existingCustomer.setName(newCustomer.getName());
	        
	        if (newCustomer.getEmail() != null) 
	        	existingCustomer.setEmail(newCustomer.getEmail());
	        
	        if (newCustomer.getPhoneNumber() != null) 
	        	existingCustomer.setPhoneNumber(newCustomer.getPhoneNumber());

	        existingCustomer = customerService.insertCustomer(existingCustomer);
	        return ResponseEntity.ok(existingCustomer);

	    } catch (DuplicateUserIdException e) {
	        dto.setMsg(e.getMessage());
	        return ResponseEntity.badRequest().body(dto);
	    } catch (ResourceNotFoundException e) {
	        dto.setMsg(e.getMessage());
	        return ResponseEntity.badRequest().body(dto);
	    }
	}

	
	
}

















