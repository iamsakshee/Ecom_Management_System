package com.springboot.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.springboot.ecom.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}
