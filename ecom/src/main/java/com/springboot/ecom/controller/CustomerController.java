package com.springboot.ecom.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.DuplicateUserIdException;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.UserService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@PostMapping("/customer/add")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer, ResponseMessageDto dto) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		customer.setUser(user);

		customerService.addCustomer(customer);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping("/customer/delete/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable int id, ResponseMessageDto dto)
			throws ResourceNotFoundException {

		customerService.validate(id);
		customerService.delete(id);

		dto.setMsg("Customer deleted!");
		return ResponseEntity.ok(dto);

	}

	@GetMapping("/customer/zipcode/get")
	public ResponseEntity<?> getCustomerByZipcode(@RequestParam String code) {
		try {
			Integer zipcode = Integer.parseInt(code);

			List<Customer> list = customerService.getCustomerByZipcode(zipcode);
			return ResponseEntity.ok(list);
		} catch (NumberFormatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PutMapping("/customer/update/{id}")
	public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer newCustomer,
			ResponseMessageDto dto) throws ResourceNotFoundException {

		Customer existingCustomer = customerService.validate(id);
		if (newCustomer.getName() != null)
			existingCustomer.setName(newCustomer.getName());

		if (newCustomer.getEmail() != null)
			existingCustomer.setEmail(newCustomer.getEmail());

		if (newCustomer.getPhoneNumber() != null)
			existingCustomer.setPhoneNumber(newCustomer.getPhoneNumber());
		existingCustomer = customerService.insertCustomer(existingCustomer);
		dto.setMsg("Customer updated successfully.");
		return ResponseEntity.ok(existingCustomer);

	}

	@GetMapping("/customer/product/get")
	public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		List<ProductResponseDto> productDtos = products.stream().map(product -> new ProductResponseDto(product.getId(),
				product.getName(), product.getPrice(), product.getStock())).collect(Collectors.toList());

		return ResponseEntity.ok(productDtos);
	}

}
