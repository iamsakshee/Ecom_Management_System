package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.model.Product;
import com.springboot.ecom.service.WishlistService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishlistController {
	
	@Autowired
	private WishlistService wishlistService;
	
	 @PostMapping("/api/wishlist/{customerId}/{productId}")
	    public ResponseEntity<String> addProductToWishlist(@PathVariable int customerId, @PathVariable int productId) {
	        try {
	            wishlistService.addProductToWishlist(customerId, productId);
	            return ResponseEntity.ok("Product added to wishlist successfully!");
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
	 
	 
	 @GetMapping("/api/wishlist/{customerId}")
	    public ResponseEntity<List<Product>> getWishlistProducts(@PathVariable int customerId) {
	        try {
	            List<Product> products = wishlistService.getWishlistProductsByCustomer(customerId);
	            return ResponseEntity.ok(products);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        }
	    }

	  
}
