package com.springboot.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.OrderResponseDto;
import com.springboot.ecom.service.CustomerProductService;


@RestController
public class CustomerProductController {
	
	@Autowired
	private CustomerProductService customerProductService;
	
	 @GetMapping("/orders/details")
	    public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails() {
	        return customerProductService.getAllOrdersWithCustomerAndProductDetails();
	    }
	
//	@GetMapping("/get-all/customer/product")
//	public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails() 
//	 {
//		List<OrderResponseDto> list 
//				=  customerProductService.getAllOrdersWithCustomerAndProductDetails();
//		return list;
//		
//		
//	}

}
