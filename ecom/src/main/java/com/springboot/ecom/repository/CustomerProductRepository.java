package com.springboot.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.CustomerProduct;

public interface CustomerProductRepository extends JpaRepository<CustomerProduct, Integer> {

	
	@Query("SELECT c.id AS customer_id, c.name AS customer_name, c.email AS customer_email, "
	        + "op.order.id AS order_id, op.order.orderStatus AS order_status, "
	        + "p.name AS product_name, p.price AS product_price, "
	        + "op.quantity AS order_quantity, cp.dateOfPurchase AS date_of_purchase "
	        + "FROM OrderProduct op "
	        + "JOIN op.order o "
	        + "JOIN op.product p "
	        + "JOIN CustomerProduct cp ON cp.product.id = p.id "
	        + "JOIN cp.customer c")
    List<Object[]> getAllOrdersWithCustomerAndProductDetails();
}
