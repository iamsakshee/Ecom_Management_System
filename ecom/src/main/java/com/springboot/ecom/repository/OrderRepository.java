package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

	
	@Query("select o from Order o join o.customer c where c.id=?1 ")
	List<Order> getOrdersByCustomerId(int customerId);

}
