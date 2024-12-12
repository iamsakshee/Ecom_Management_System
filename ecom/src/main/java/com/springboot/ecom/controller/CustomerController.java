package com.springboot.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.ecom.dto.CustomerShippingDetailsDto;
import com.springboot.ecom.dto.ProductResponseDto;
import com.springboot.ecom.dto.ResponseMessageDto;
import com.springboot.ecom.exception.ResourceNotFoundException;
import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Product;
import com.springboot.ecom.model.User;
import com.springboot.ecom.repository.ProductRepository;
import com.springboot.ecom.service.CustomerService;
import com.springboot.ecom.service.ProductService;
import com.springboot.ecom.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    
    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @PostMapping("/customer/register")
    public Customer registerCustomer(@RequestBody Customer customer, ResponseMessageDto dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        customer.setUser(user);

        logger.info("Registering customer: {}", customer);
        return customerService.insert(customer);
    }

    @DeleteMapping("/customer/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int id, ResponseMessageDto dto)
            throws ResourceNotFoundException {

        logger.info("Deleting customer with id: {}", id);
        customerService.validate(id);
        customerService.delete(id);

        dto.setMsg("Customer deleted!");
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/customer/zipcode/get")
    public ResponseEntity<?> getCustomerByZipcode(@RequestParam String code) {
        try {
            Integer zipcode = Integer.parseInt(code);
            logger.info("Fetching customers with zipcode: {}", zipcode);

            List<Customer> list = customerService.getCustomerByZipcode(zipcode);
            return ResponseEntity.ok(list);
        } catch (NumberFormatException e) {
            logger.error("Invalid zipcode format: {}", code, e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/customer/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int id, @RequestBody Customer newCustomer,
            ResponseMessageDto dto) throws ResourceNotFoundException {

        logger.info("Updating customer with id: {}", id);
        Customer existingCustomer = customerService.validate(id);
        if (newCustomer.getName() != null)
            existingCustomer.setName(newCustomer.getName());

        if (newCustomer.getEmail() != null)
            existingCustomer.setEmail(newCustomer.getEmail());

        if (newCustomer.getPhoneNumber() != null)
            existingCustomer.setPhoneNumber(newCustomer.getPhoneNumber());

        existingCustomer = customerService.insertCustomer(existingCustomer);
        dto.setMsg("Customer updated successfully.");
        logger.info("Customer updated successfully with id: {}", id);
        return ResponseEntity.ok(existingCustomer);
    }

    @GetMapping("/customer/product/get")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        logger.info("Fetching all products");
        List<Product> products = productService.getAllProducts();

        List<ProductResponseDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            ProductResponseDto dto = new ProductResponseDto(product.getId(), product.getName(), product.getPrice(),
                    product.getStock());
            productDtos.add(dto);
        }

        logger.info("Fetched {} products", products.size());
        return ResponseEntity.ok(productDtos);
    }
    
    
    @GetMapping("/customers/get/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            logger.info("No customers found");
            return ResponseEntity.noContent().build();
        }
        logger.info("Fetched {} customers", customers.size());
        return ResponseEntity.ok(customers);
    } 
    
    @GetMapping("/customer/details")
    public CustomerShippingDetailsDto getCustomerAndShippingDetailsByUsername(@RequestParam String username) {
        logger.info("Fetching customer and shipping details for username: {}", username);
        return customerService.getCustomerAndShippingDetailsByUsername(username);
    }
}
