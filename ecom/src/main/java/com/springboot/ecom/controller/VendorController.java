package com.springboot.ecom.controller;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.dto.VendorOrderProductDto;
import com.springboot.ecom.enums.OrderStatus;
import com.springboot.ecom.exception.DuplicateEntryException;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.model.Vendor;
import com.springboot.ecom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Autowired
    private OrderService orderService;

    @PostMapping("/vendor/add")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor, ResponseMessageDto dto) throws ResourceNotFoundException, DuplicateEntryException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        vendor.setUser(user);
        vendorService.addVendor(vendor);
        return ResponseEntity.ok(vendor);
    }

    @GetMapping("vendor/getDetails")
    public Vendor getVendorDetails(@RequestParam String username)
    {
        return vendorService.getVendorDetailsByUsername(username);
    }

    @PostMapping("/vendor/update/{id}")
    public ResponseEntity<?> updateVendor(@RequestBody Vendor newVendor, @PathVariable int id) throws ResourceNotFoundException, InvalidUsernameException {
        Vendor existingVendor = vendorService.getVendorById(id);
        if (newVendor.getCompanyName() != null) {
            existingVendor.setCompanyName(newVendor.getCompanyName());
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
        newVendor = vendorService.saveVendor(existingVendor);
        return ResponseEntity.ok(newVendor);
    }


    @DeleteMapping("vendor/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws ResourceNotFoundException, InvalidUsernameException {
        vendorService.getVendorById(id);
        vendorService.deleteById(id);
        return ResponseEntity.ok("Vendor deleted");
    }

    @GetMapping("/orders/{vendorId}/{orderStatus}")
    public ResponseEntity<List<VendorOrderProductDto>> getOrdersByVendorIdAndStatus(
            @PathVariable int vendorId,
            @PathVariable OrderStatus orderStatus) throws ResourceNotFoundException {  // Accepting the orderStatus as path variable

        List<VendorOrderProductDto> orders = orderService.getProductsWithStatusByVendorIdAndStatus(vendorId, orderStatus);

        if (orders.isEmpty()) {
            throw new ResourceNotFoundException("No orders found");
        } else {
            return ResponseEntity.ok(orders);
        }
    }

    @GetMapping("/vendor/all")
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }

    @GetMapping("/orders/{vendorId}")
    public List<VendorOrderProductDto> getAllOrdersForVendor(@PathVariable int vendorId) {
        return orderService.getAllOrdersByVendorId(vendorId);
    }
}
