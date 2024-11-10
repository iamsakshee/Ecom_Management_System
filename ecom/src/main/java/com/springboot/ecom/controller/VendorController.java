package com.springboot.ecom.controller;

import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.service.CategoryService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/vendor/add")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor) {
        Vendor savedVendor = vendorService.addVendor(vendor);
        return ResponseEntity.ok(savedVendor);
    }

    @GetMapping("/vendor/all")
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorService.getAllVendors();
        return vendors;
    }

    @GetMapping("/vendor/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable int id) {
        try {
            Vendor vendor = vendorService.getVendorById(id);
            return ResponseEntity.ok(vendor);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/vendor/getProducts/{id}")
    public ResponseEntity<Set<Product>> getProductsByVendorId(@PathVariable int id){
        return ResponseEntity.ok(productService.findProductsByVendor(id));
    }
}
