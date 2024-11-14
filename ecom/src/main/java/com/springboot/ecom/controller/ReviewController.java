package com.springboot.ecom.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.Review;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.ReviewService;

@RestController
public class ReviewController {
	
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/customer/add/review/{cid}/{pid}")
	 public ResponseEntity<?> addReview(
	            @PathVariable int cid, 
	            @PathVariable int pid, 
	            @RequestBody Review review, 
	            ResponseMessageDto dto) throws ResourceNotFoundException 
	{
		
		Customer customer = null;
		customer = customerService.validate(cid);
		
		Product product = null;
		product = productService.validate(pid);
		
		review.setCustomer(customer);
		review.setProduct(product);
		review.setDate(LocalDateTime.now());
		
		reviewService.addReview(review);
		return ResponseEntity.ok("Review added");
		
	}
	
	
	@DeleteMapping("/customer/review/delete/{cid}")
	public ResponseEntity<?> deleteReviewbyCustomerId(@PathVariable int cid, ResponseMessageDto dto) throws ResourceNotFoundException
	{
		Customer customer = null;
		customer = customerService.validate(cid);
		
		reviewService.deleteReviewbyCustomerId(cid);
		dto.setMsg("Review deleted!");
		return ResponseEntity.ok(dto);

	}
	
	

}
