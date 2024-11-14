package com.springboot.ecom.controller;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.AdminService;
import com.springboot.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin/auth/switch-status/{id}")
    public ResponseEntity<User> updateUserStatus(@PathVariable int id,
                                                 @RequestParam boolean status) throws ResourceNotFoundException {
        User user = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/getAllProducts")
    public List<Product> getAllProducts() {
        return adminService.getAllProducts();
    }
}
