package com.springboot.ecom.service;

import com.springboot.ecom.model.OrderProduct;
import com.springboot.ecom.repository.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    public OrderProduct insert(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

}
