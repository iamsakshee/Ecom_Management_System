package com.springboot.ecom.controller;

import com.springboot.ecom.dto.VendorOrderProductDto;
import com.springboot.ecom.enums.OrderStatus;
import com.springboot.ecom.exception.InvalidUsernameException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/vendor/update/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable int id, @RequestBody User newUser) throws ResourceNotFoundException, InvalidUsernameException {
        User existingUser = userService.findByUserId(id);
        if (newUser.getCompanyName() != null) {
            existingUser.setCompanyName(newUser.getCompanyName());
        }
        if (newUser.getEmail() != null) {
            existingUser.setEmail(newUser.getEmail());
        }
        if (newUser.getPhone() != null) {
            existingUser.setPhone(newUser.getPhone());
        }
        if (newUser.getAddress() != null) {
            existingUser.setAddress(newUser.getAddress());
        }
        existingUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(existingUser);
    }


    @DeleteMapping("vendor/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws ResourceNotFoundException, InvalidUsernameException {
        userService.findByUserId(id);
        userService.deleteById(id);
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

    @GetMapping("/orders/{vendorId}")
    public List<VendorOrderProductDto> getAllOrdersForVendor(@PathVariable int vendorId) {
        return orderService.getAllOrdersByVendorId(vendorId);
    }
}
