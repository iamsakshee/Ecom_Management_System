package com.springboot.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.ecom.model.Customer;
import com.springboot.ecom.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer>{

	Optional<Wishlist> findByCustomer(Customer customer);
	
	 
}
