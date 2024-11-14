package com.springboot.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecom.model.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {
}
