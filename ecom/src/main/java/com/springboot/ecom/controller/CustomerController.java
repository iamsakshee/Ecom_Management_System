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
	
	
	@PostMapping("/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer newcustomer, 
											ResponseMessageDto dto)
	{
		try {
			Customer existingCustomer = customerService.validate(id);
			if(newcustomer.getName()!=null)
				existingCustomer.setName(newcustomer.getName());
			
			if(newcustomer.getEmail()!=null)
				existingCustomer.setEmail(newcustomer.getEmail());
			
			if(newcustomer.getPhoneNumber()!=null)
				existingCustomer.setPhoneNumber(newcustomer.getPhoneNumber());
			
			existingCustomer = customerService.insertCustomer(existingCustomer);
			return ResponseEntity.ok(existingCustomer);
			
			
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
}

















