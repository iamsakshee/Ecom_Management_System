//package com.springboot.ecom.model;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import com.springboot.ecom.enums.OrderStatus;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.Id;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class Order {
//	
//	@Id
//	private int id;
//	
//	private LocalDateTime orderDate;
//	
//	@Enumerated(EnumType.STRING)
//	private OrderStatus orderStatus;
//	
//	@ManyToOne
//	private Customer customer;
//	
//
//	// private Shipment shipment;
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public LocalDateTime getOrderDate() {
//		return orderDate;
//	}
//
//	public void setOrderDate(LocalDateTime orderDate) {
//		this.orderDate = orderDate;
//	}
//
//	public OrderStatus getorderStatus() {
//		return orderStatus;
//	}
//
//	public void setorderStatus(OrderStatus orderStatus) {
//		this.orderStatus = orderStatus;
//	}
//
//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//	
//	
//	
//
//}
