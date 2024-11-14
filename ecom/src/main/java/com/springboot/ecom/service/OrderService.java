package com.springboot.ecom.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Order;
import com.springboot.ecom.model.OrderProduct;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;

	public Order insert(Order order) {
		
		return orderRepository.save(order);
	}

	public List<Order> getOrdersByCustomerId(int customerId) {
		
		return orderRepository.getOrdersByCustomerId(customerId);
	}

	 

}
