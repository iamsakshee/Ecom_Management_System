package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Delivery;
import com.springboot.ecom.service.DeliveryService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;
	
	@PostMapping("/delivery/add")
	public Delivery addUser(@RequestBody Delivery user) {
		 System.out.println(user);
		return deliveryService.insert(user);
	}
	
	@GetMapping("/delivery/all")
	public List<Delivery> getAllUser() {
		List<Delivery> list = deliveryService.getAllDelivery();
		return list;
	}
	
	@DeleteMapping("/delivery/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id, ResponseMessageDto dto) {
		try {
			deliveryService.validate(id);
			deliveryService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("Delivery Deleted");
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping("/delivery/addDetails")
	public ResponseEntity<?> addDeliveryDetails(@RequestBody Delivery delivery) {
	        deliveryService.saveDelivery(delivery); // Method to save delivery details in DB
	        return ResponseEntity.ok("Delivery details added successfully");

	}

}
	