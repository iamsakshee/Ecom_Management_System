package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.model.ShippingAddress;
import com.springboot.ecom.service.ShippingAddressService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class ShippingAddressController {
	
	@Autowired
	private ShippingAddressService shippingAddressService; 

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
	

}
