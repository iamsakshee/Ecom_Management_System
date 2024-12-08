package com.springboot.ecom.controller;

import java.util.ArrayList;
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

import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.ProductImage;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.WishlistService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class WishlistController {
	
	@Autowired
	private WishlistService wishlistService;
	
	@Autowired
	private ProductService productService;
	
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
	 public ResponseEntity<List<ProductResponseDto>> getWishlistProducts(@PathVariable int customerId) throws ResourceNotFoundException {
	     // Fetch all wishlist products by customer ID
	     List<Product> products = wishlistService.getWishlistProductsByCustomer(customerId);

	     // Initialize a list to hold ProductResponseDto objects
	     List<ProductResponseDto> listDto = new ArrayList<>();

	     // Loop through each product and map it to a ProductResponseDto
	     for (Product product : products) {
	         // Fetch all images for the current product
	         List<ProductImage> imageList = productService.getAllProductImagesByProductId(product.getId());

	         // Map product to ProductResponseDto
	         ProductResponseDto dto = new ProductResponseDto();
	         dto.setId(product.getId());
	         dto.setName(product.getName());
	         dto.setStock(product.getStock());
	         dto.setPrice(product.getPrice());
	         dto.setDescription(product.getDescription());

	         // Filter and set product-specific images (same as in your previous example)
	         List<ProductImage> iList = imageList.stream()
	                 .filter(i -> i.getProduct().getId() == product.getId())
	                 .toList();
	         dto.setImages(iList);

	         // Add the DTO to the list
	         listDto.add(dto);
	     }

	     // Return the list of ProductResponseDto objects
	     return ResponseEntity.ok(listDto);
	 }

	 
	 @DeleteMapping("/wishlist/product/delete/{pid}")
	    public ResponseEntity<?> deleteWishlistProductById(@PathVariable int pid, ResponseMessageDto dto)
	            throws ResourceNotFoundException {

	      
	        productService.getProductById(pid);
	        wishlistService.deleteWishlistProductById(pid);

	        dto.setMsg("Product from wishlist deleted!");
	        return ResponseEntity.ok(dto);
	    }
	    
}