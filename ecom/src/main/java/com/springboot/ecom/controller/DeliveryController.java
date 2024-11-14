package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class DeliveryController {

	@Autowired
	private DeliveryService DeliveryService;
	
	@PostMapping("/Delivery/add")
	public Delivery addUser(@RequestBody Delivery user) {
		 System.out.println(user);
		return DeliveryService.insert(user);
	}
	
	@GetMapping("/Delivery/all")
	public List<Delivery> getAllUser() {
		List<Delivery> list = DeliveryService.getAllDelivery();
		return list;
	}
	
	@DeleteMapping("/Delivery/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id, ResponseMessageDto dto) {
		try {
			DeliveryService.validate(id);
			DeliveryService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("Delivery Deleted");
		return ResponseEntity.ok(dto);
	}
}
	