package com.springboot.ecom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.ecom.model.Review;
import com.springboot.ecom.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public void addReview(Review review) {

		reviewRepository.save(review);

	}

	public void deleteReviewbyCustomerId(int cid) {

		reviewRepository.deleteReviewbyCustomerId(cid);
	}

}
