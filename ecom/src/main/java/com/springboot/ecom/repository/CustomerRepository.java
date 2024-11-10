package com.springboot.ecom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	
	@Query("select c from ShippingAddress sa join sa.customer c where sa.zipCode=?1")
	List<Customer> getCustomerByZipcode(Integer zipcode);
	
}
