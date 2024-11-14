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
import com.springboot.ecom.model.Manager;
import com.springboot.ecom.service.ManagerService;

@RestController
public class ManagerController {

	@Autowired
	private ManagerService ManagerService;
	
	@PostMapping("/Manager/add")
	public Manager addUser(@RequestBody Manager user) {
		 System.out.println(user);
		return ManagerService.insert(user);
	}
	
	@GetMapping("/Manager/all")
	public List<Manager> getAllUser() {
		List<Manager> list = ManagerService.getAllManager();
		return list;
	}
	
	@DeleteMapping("/Manager/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id, ResponseMessageDto dto) {
		try {
			ManagerService.validate(id);
			ManagerService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("Manager Deleted");
		return ResponseEntity.ok(dto);
	}
}
	