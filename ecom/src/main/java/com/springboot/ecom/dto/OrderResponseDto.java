package com.springboot.ecom.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
@Component
public class OrderResponseDto {
	 private int customer_id;
	    private String customer_name;
	    private String customer_email;
	    private int order_id;
	    private String order_status;
	    private String product_name;
	    private double product_price;
	    private int order_quantity;
	    private LocalDate date_of_purchase;
		public int getCustomer_id() {
			return customer_id;
		}
		public void setCustomer_id(int customer_id) {
			this.customer_id = customer_id;
		}
		public String getCustomer_name() {
			return customer_name;
		}
		public void setCustomer_name(String customer_name) {
			this.customer_name = customer_name;
		}
		public String getCustomer_email() {
			return customer_email;
		}
		public void setCustomer_email(String customer_email) {
			this.customer_email = customer_email;
		}
		public int getOrder_id() {
			return order_id;
		}
		public void setOrder_id(int order_id) {
			this.order_id = order_id;
		}
		public String getOrder_status() {
			return order_status;
		}
		public void setOrder_status(String order_status) {
			this.order_status = order_status;
		}
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		public double getProduct_price() {
			return product_price;
		}
		public void setProduct_price(double product_price) {
			this.product_price = product_price;
		}
		public int getOrder_quantity() {
			return order_quantity;
		}
		public void setOrder_quantity(int order_quantity) {
			this.order_quantity = order_quantity;
		}
		public LocalDate getDate_of_purchase() {
			return date_of_purchase;
		}
		public void setDate_of_purchase(LocalDate date_of_purchase) {
			this.date_of_purchase = date_of_purchase;
		}
	
	    

	}
