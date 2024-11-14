package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.UserService;
import com.springboot.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @PostMapping("/vendor/add")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor, ResponseMessageDto dto) throws ResourceNotFoundException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        vendor.setUser(user);
        vendorService.addVendor(vendor);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/vendor/all")
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return vendors;
    }

    @GetMapping("/vendor/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable int id) throws ResourceNotFoundException {
        Vendor vendor = vendorService.getVendorById(id);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("/vendor/getProducts/{id}")
    public ResponseEntity<?> getProductsByVendorId(@PathVariable int id) throws ResourceNotFoundException {
        Set<Product> products = productService.findProductsByVendor(id);
        return ResponseEntity.ok(products);
    }


    @PutMapping("/vendor/update/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable int id, @RequestBody Vendor newVendor) throws ResourceNotFoundException {
        Vendor existingVendor = vendorService.getVendorById(id);
        if (newVendor.getCompany_name() != null) {
            existingVendor.setCompany_name(newVendor.getCompany_name());
        }
        if (newVendor.getEmail() != null) {
            existingVendor.setEmail(newVendor.getEmail());
        }
        if (newVendor.getPhone() != null) {
            existingVendor.setPhone(newVendor.getPhone());
        }
        if (newVendor.getAddress() != null) {
            existingVendor.setAddress(newVendor.getAddress());
        }
        existingVendor = vendorService.saveVendor(existingVendor);
        return ResponseEntity.ok(existingVendor);
    }


    @DeleteMapping("vendor/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws ResourceNotFoundException {
        vendorService.getVendorById(id);
        vendorService.deleteById(id);
        return ResponseEntity.ok("Vendor deleted");
    }
}
