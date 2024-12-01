package com.springboot.ecom.repository;

import com.springboot.ecom.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("DELETE FROM Review r where r.customer.id = ?1")
    void deleteReviewByCustomerId(int cid);
}
