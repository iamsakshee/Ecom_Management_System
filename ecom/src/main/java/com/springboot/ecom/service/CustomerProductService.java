package com.springboot.ecom.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.dto.OrderResponseDto;
import com.springboot.ecom.model.CustomerProduct;
import com.springboot.ecom.repository.CustomerProductRepository;

@Service
public class CustomerProductService {
	
	@Autowired
	private CustomerProductRepository customerProductRepository;

	public void insert(CustomerProduct customerProduct) {
		
		customerProductRepository.save(customerProduct);
		
	}
	
	public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails() {
        List<Object[]> orderData = customerProductRepository.getAllOrdersWithCustomerAndProductDetails();
        List<OrderResponseDto> list = new ArrayList<>();

        for (Object[] obj : orderData) {
            int customerId = (int) obj[0];
            String customerName = (String) obj[1];
            String customerEmail = (String) obj[2];
            int orderId = (int) obj[3];
            String orderStatus = obj[4].toString();
            String productName = (String) obj[5];
            double productPrice = (double) obj[6];
            int orderQuantity = (int) obj[7];
            LocalDate dateOfPurchase = (LocalDate) obj[8];

            OrderResponseDto dto = new OrderResponseDto();
            dto.setCustomer_id(customerId);
            dto.setCustomer_name(customerName);
            dto.setCustomer_email(customerEmail);
            dto.setOrder_id(orderId);
            dto.setOrder_status(orderStatus);
            dto.setProduct_name(productName);
            dto.setProduct_price(productPrice);
            dto.setOrder_quantity(orderQuantity);
            dto.setDate_of_purchase(dateOfPurchase);

            list.add(dto);
        }
        return list;
    }


	 public List<OrderResponseDto> getAllOrdersWithCustomerAndProductDetails1(int cid) {
	        List<Object[]> orderData = customerProductRepository.getAllOrdersWithCustomerAndProductDetails1(cid);
	        List<OrderResponseDto> list = new ArrayList<>();
	        Map<Integer, OrderResponseDto> orderMap = new HashMap<>();

	        for (Object[] obj : orderData) {
	            int customerId = (int) obj[0];
	            String customerName = (String) obj[1];
	            String customerEmail = (String) obj[2];
	            int orderId = (int) obj[3];
	            String orderStatus = obj[4].toString();
	            String productName = (String) obj[5];
	            double productPrice = (double) obj[6];
	            int orderQuantity = (int) obj[7];
	            LocalDate dateOfPurchase = (LocalDate) obj[8];

	            // Check if this order ID is already in the map
	            OrderResponseDto dto = orderMap.get(orderId);

	            if (dto == null) {
	                // If not found, create a new entry
	                dto = new OrderResponseDto();
	                dto.setCustomer_id(customerId);
	                dto.setCustomer_name(customerName);
	                dto.setCustomer_email(customerEmail);
	                dto.setOrder_id(orderId);
	                dto.setOrder_status(orderStatus);
	                dto.setProduct_name(productName);
	                dto.setProduct_price(productPrice);
	                dto.setDate_of_purchase(dateOfPurchase);
	                dto.setOrder_quantity(orderQuantity);
	                orderMap.put(orderId, dto);
	            }

	            // Accumulate the quantities
//	            dto.setOrder_quantity(dto.getOrder_quantity());
	        }

	        // Convert map values to a list
	        list.addAll(orderMap.values());
	        return list;
	    }
	

}
