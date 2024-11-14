package com.springboot.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.OrderProduct;
import com.springboot.ecom.repository.OrderProductRepository;

@Service
public class OrderProductService {
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	public OrderProduct insert(OrderProduct orderProduct) {
		
		return orderProductRepository.save(orderProduct);
	}

}
