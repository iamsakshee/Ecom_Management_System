package com.springboot.ecom.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Order;
import com.springboot.ecom.model.User;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	
	@Query("select c from ShippingAddress sa join sa.customer c where sa.zipCode=?1")
	List<Customer> getCustomerByZipcode(Integer zipcode);

	Optional<Customer> findByUser(User user);

	Optional<Customer> findByEmail(String email);
	
	@Query("SELECT sa FROM ShippingAddress sa JOIN FETCH sa.customer c JOIN FETCH c.user u WHERE u.username = ?1")
	Customer getCustomerDetailsByUsername(String username);
	

    @Query("SELECT c.id, c.name, c.email, c.phoneNumber FROM Customer c "
         + "JOIN c.user u WHERE u.username = :username")
    List<Object[]> findCustomerDetailsByUsername(@Param("username") String username);

    
    
	
}
