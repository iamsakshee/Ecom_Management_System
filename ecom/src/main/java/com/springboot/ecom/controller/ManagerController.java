package com.springboot.ecom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Manager;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Warehouse;
import com.springboot.ecom.service.ManagerService;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.service.WarehouseService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WarehouseService warehouseService;

	
	@PostMapping("/manager/add")
    public ResponseEntity<?> addVendor(@RequestBody Manager manager, ResponseMessageDto dto) throws ResourceNotFoundException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        Warehouse warehouse = warehouseService.findById(1);
        manager.setUser(user);
        manager.setWarehouse(warehouse);
        managerService.insert(manager);
        return ResponseEntity.ok(manager);
    }
	
	@GetMapping("/manager/all")
	public List<Manager> getAllUser() {
		List<Manager> list = managerService.getAllManager();
		return list;
	}
	
	@GetMapping("/manager/username")
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
	
	@PutMapping("/update/manager")
	public Manager postManager(@RequestParam String username, @RequestBody Manager manager) {
		return managerService.updateManager(username,manager);

	}

}
	