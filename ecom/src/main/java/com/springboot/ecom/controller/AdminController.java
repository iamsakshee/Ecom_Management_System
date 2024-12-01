package com.springboot.ecom.controller;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.AdminService;
import com.springboot.ecom.service.ProductService;
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
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/auth/switch-status/{id}")
    public ResponseEntity<User> updateUserStatus(@PathVariable int id,
                                                 @RequestParam boolean status) throws ResourceNotFoundException {
        User user = adminService.updateUserStatus(id, status);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/getAllProducts")
    public List<Product> getAllProducts() throws ResourceNotFoundException {
        return adminService.getAllProducts();
    }

    @GetMapping("/admin/getFeaturedProducts")
    public List<Product> getAllFeaturedProductRequests() throws ResourceNotFoundException {
        return adminService.getAllProducts();
    }

    @PutMapping("/admin/approve/product/{productId}")
    public ResponseEntity<?> updateFeaturedProduct(@PathVariable int productId) throws ResourceNotFoundException {
        Product existingProduct = productService.getProductById(productId);

        adminService.updateFeaturedStatus(existingProduct);
        return ResponseEntity.ok(existingProduct);
    }
}
