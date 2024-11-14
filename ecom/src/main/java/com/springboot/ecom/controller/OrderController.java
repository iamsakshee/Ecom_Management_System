package com.springboot.ecom.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.enums.OrderStatus;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.CustomerProduct;
import com.springboot.ecom.model.Order;
import com.springboot.ecom.model.OrderProduct;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.service.CustomerProductService;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.OrderProductService;
import com.springboot.ecom.service.OrderService;
import com.springboot.ecom.service.ProductService;

@RestController
public class OrderController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderProductService orderProductService;

	@Autowired
	private CustomerProductService customerProductService;

	@PostMapping("/customer/product/purchase/{customerId}/{productId}")
	public ResponseEntity<?> purchaseProduct(@PathVariable int customerId, @PathVariable int productId,
			@RequestParam int quantity, ResponseMessageDto dto) throws ResourceNotFoundException {

		Customer customer = customerService.validate(customerId);
		Product product = productService.validate(productId);

		if (product.getStock() < quantity) {
			dto.setMsg("Insufficient stock for the product.");
			return ResponseEntity.badRequest().body(dto);
		}

		product.setStock(product.getStock() - quantity);
		productService.updateProduct(product);

		Order order = new Order();
		order.setCustomer(customer);
		order.setOrderDate(LocalDateTime.now());
		order.setorderStatus(OrderStatus.PENDING);
		order = orderService.insert(order);

		OrderProduct orderProduct = new OrderProduct();
		orderProduct.setOrder(order);
		orderProduct.setProduct(product);
		orderProduct.setQuantity(quantity);
		orderProductService.insert(orderProduct);

		CustomerProduct customerProduct = new CustomerProduct();
		customerProduct.setCustomer(customer);
		customerProduct.setProduct(product);
		customerProduct.setQuantity(quantity);
		customerProduct.setDateOfPurchase(LocalDate.now());
		customerProduct.setOrderStatus(OrderStatus.PENDING);
		customerProductService.insert(customerProduct);

		dto.setMsg("Product purchased successfully!");
		return ResponseEntity.ok(dto);
	}
	
	
}
	

