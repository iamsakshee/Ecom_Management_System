package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ResponseMessageDto;
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
    public ResponseEntity<Set<Product>> getProductsByVendorId(@PathVariable int id) {
        return ResponseEntity.ok(productService.findProductsByVendor(id));
    }

    @PutMapping("/vendor/update/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable int id, @RequestBody Vendor newVendor, ResponseMessageDto dto) {
        try {
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
            existingVendor = vendorService.addVendor(existingVendor);
            return ResponseEntity.ok(existingVendor);
        } catch (ResourceNotFoundException e) {
            dto.setMsg(e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
    }

    @DeleteMapping("vendor/delete/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable int id, ResponseMessageDto dto)
    {
        try {
            vendorService.getVendorById(id);
            vendorService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            dto.setMsg(dto.getMsg());
            return ResponseEntity.badRequest().body(dto);
        }
        dto.setMsg("Vendor deleted");
        return ResponseEntity.ok(dto);
    }

}
