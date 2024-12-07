package com.springboot.ecom.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
@CrossOrigin(origins = "http://localhost:4200")
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

    // Logger for logging activities in OrderController
    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/customer/product/purchase/{customerId}/{productId}")
    public ResponseEntity<?> purchaseProduct(@PathVariable int customerId, @PathVariable int productId,
            @RequestParam int quantity, ResponseMessageDto dto) throws ResourceNotFoundException {

        logger.info("Processing purchase for customerId: {} and productId: {} with quantity: {}", customerId, productId, quantity);

        Customer customer = customerService.validate(customerId);
        Product product = productService.getProductById(productId);

        if (product.getStock() < quantity) {
            logger.warn("Insufficient stock for productId: {}. Requested quantity: {}, Available stock: {}", 
                        productId, quantity, product.getStock());
            dto.setMsg("Insufficient stock for the product.");
            return ResponseEntity.badRequest().body(dto);
        }

        logger.info("Sufficient stock available for productId: {}. Proceeding with the purchase.", productId);

        product.setStock(product.getStock() - quantity);
        productService.updateProduct(product);
        logger.info("Updated stock for productId: {}. New stock: {}", productId, product.getStock());

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setorderStatus(OrderStatus.PENDING);
        order = orderService.insert(order);
        logger.info("Order created with orderId: {} for customerId: {}", order.getId(), customerId);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(quantity);
        orderProductService.insert(orderProduct);
        logger.info("OrderProduct added: orderId: {}, productId: {}, quantity: {}", order.getId(), productId, quantity);

        CustomerProduct customerProduct = new CustomerProduct();
        customerProduct.setCustomer(customer);
        customerProduct.setProduct(product);
        customerProduct.setQuantity(quantity);
        customerProduct.setDateOfPurchase(LocalDate.now());
        customerProduct.setOrderStatus(OrderStatus.PENDING);
        customerProductService.insert(customerProduct);
        logger.info("CustomerProduct added for customerId: {}, productId: {}, quantity: {}", customerId, productId, quantity);

        dto.setMsg("Product purchased successfully!");
        logger.info("Product purchase successful for customerId: {} and productId: {}", customerId, productId);
        return ResponseEntity.ok(dto);
    }
}
