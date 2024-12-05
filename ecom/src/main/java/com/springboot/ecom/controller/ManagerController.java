package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Manager;
import com.springboot.ecom.service.ManagerService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
//	@PostMapping("/manager/add")
//	public Manager addUser(@RequestBody Manager user) {
//		return managerService.insert(user);
//	}
	
	@PostMapping("/add/manager")
	public Manager addManager(@RequestParam String username, @RequestBody Manager manager) {
		return managerService.addManager(username, manager);
	}
	
	@GetMapping("/manager/all")
	public List<Manager> getAllUser() {
		List<Manager> list = managerService.getAllManager();
		return list;
	}
	
	@GetMapping("manager/username")
    public Optional<Manager> getManagerByUsername(@RequestParam String username) {
        return managerService.getManagerByUsername(username);
    }
	
	@DeleteMapping("/manager/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id, ResponseMessageDto dto) {
		try {
			managerService.validate(id);
			managerService.delete(id);
		} catch (ResourceNotFoundException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		} 
		dto.setMsg("manager Deleted");
		return ResponseEntity.ok(dto);
	}
}
	