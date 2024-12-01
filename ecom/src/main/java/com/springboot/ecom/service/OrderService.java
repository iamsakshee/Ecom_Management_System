package com.springboot.ecom.service;

import com.springboot.ecom.dto.VendorOrderProductDto;
import com.springboot.ecom.enums.OrderStatus;
import com.springboot.ecom.model.Order;
import com.springboot.ecom.repository.OrderProductRepository;
import com.springboot.ecom.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private OrderProductRepository orderProductRepository;

    public List<VendorOrderProductDto> getProductsWithStatusByVendorIdAndStatus(int vendorId, OrderStatus orderStatus) {
        List<Object[]> result = orderProductRepository.findProductsForVendorWithStatus(vendorId, orderStatus);
        List<VendorOrderProductDto> orderProductDtos = new ArrayList<>();

        for (Object[] obj : result) {
            VendorOrderProductDto dto = new VendorOrderProductDto();
            dto.setOrderId((Integer) obj[0]);
            dto.setOrderDate((LocalDateTime) obj[1]);
            dto.setCustomerName((String) obj[2]);
            dto.setCustomerEmail((String) obj[3]);
            dto.setProductName((String) obj[4]);
            dto.setProductPrice((Double) obj[5]);
            dto.setQuantity((Integer) obj[6]);
            dto.setOrderStatus((OrderStatus) obj[7]);
            orderProductDtos.add(dto);
        }

        return orderProductDtos;
    }

    public List<VendorOrderProductDto> getAllOrdersByVendorId(int vendorId) {
        List<Object[]> result = orderProductRepository.findAllOrdersForVendor(vendorId);
        List<VendorOrderProductDto> orderProductDtos = new ArrayList<>();

        for (Object[] obj : result) {
            VendorOrderProductDto dto = new VendorOrderProductDto();
            dto.setOrderId((Integer) obj[0]);
            dto.setOrderDate((LocalDateTime) obj[1]);
            dto.setCustomerName((String) obj[2]);
            dto.setCustomerEmail((String) obj[3]);
            dto.setProductName((String) obj[4]);
            dto.setProductPrice((Double) obj[5]);
            dto.setQuantity((Integer) obj[6]);
            dto.setOrderStatus((OrderStatus) obj[7]);
            orderProductDtos.add(dto);
        }

        return orderProductDtos;
    }
}