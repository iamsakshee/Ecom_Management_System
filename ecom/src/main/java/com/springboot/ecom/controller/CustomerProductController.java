package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.OrderResponseDto;
import com.springboot.ecom.service.CustomerProductService;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerProductController {
	
	@Autowired
	private CustomerProductService customerProductService;
	
	 @GetMapping("/orders/details")
	    public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails() {
	        return customerProductService.getAllOrdersWithCustomerAndProductDetails();
	    }

	 @GetMapping("/orders/details/{cid}")
	    public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails1(@PathVariable int cid) {
	        return customerProductService.getAllOrdersWithCustomerAndProductDetails1(cid);
	    }
	 

}
