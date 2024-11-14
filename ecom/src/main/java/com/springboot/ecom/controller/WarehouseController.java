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
import com.springboot.ecom.model.Warehouse;
import com.springboot.ecom.service.WarehouseService;

@RestController
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;
	
	@PostMapping("/Warehouse/add")
	public Warehouse addUser(@RequestBody Warehouse user) {
		 System.out.println(user);
		return warehouseService.insert(user);
	}
	
	@GetMapping("/Warehouse/all")
	public List<Warehouse> getAllWarehouse() {
		List<Warehouse> list = warehouseService.getAllWarehouse();
		return list;
	}
	
	@DeleteMapping("/Warehouse/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id, ResponseMessageDto dto) {
		try {
			warehouseService.validate(id);
			warehouseService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("Warehouse Deleted");
		return ResponseEntity.ok(dto);
	}
}
	