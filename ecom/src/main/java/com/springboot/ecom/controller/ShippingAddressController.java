package com.springboot.ecom.controller;


import java.util.List;

import org.apache.catalina.connector.Response;
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
import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.ShippingAddressService;

@RestController
public class ShippingAddressController {
	
	
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Autowired
	private CustomerService customerService;
	
	
	@PostMapping("/customer/address/post/{cid}")
	public ResponseEntity<?> insertShippingaddress(@PathVariable int cid, @RequestBody ShippingAddress shippingAddress, 
										ResponseMessageDto dto)
	{
		Customer customer = null;
		try {
			customer =customerService.validate(cid);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		
		shippingAddress.setCustomer(customer);
		
		shippingAddressService.insertShippingAddress(shippingAddress);
		return null;
			
	}
	
	
	@GetMapping("/address/city/get")
	public ResponseEntity<?> getAddressByCity(@RequestParam String city)
	{
		try {
		List<ShippingAddress> list = shippingAddressService.getAddressByCity(city);
		return ResponseEntity.ok(list);
		}
		catch (IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@DeleteMapping("/address/delete/{cid}")
	public ResponseEntity<?> deleteAddressbyCustomerId(@PathVariable int cid, ResponseMessageDto dto)
	{
		try {
			customerService.validate(cid);
			shippingAddressService.deleteAddressbyCustomerId(cid);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
		dto.setMsg("Shipping address deleted!!");
		return ResponseEntity.ok(dto);
	}
	
	

	
		
}











