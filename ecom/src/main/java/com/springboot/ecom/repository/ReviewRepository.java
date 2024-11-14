package com.springboot.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.springboot.ecom.model.Review;

import jakarta.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Integer>{

	@Modifying
	@Transactional
	@Query("DELETE FROM Review r where r.customer.id = ?1")
	void deleteReviewbyCustomerId(int cid);


	
	 

}
