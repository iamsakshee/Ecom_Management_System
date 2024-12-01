package com.springboot.ecom.service;

import com.springboot.ecom.model.Review;
import com.springboot.ecom.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public void addReview(Review review) {

        reviewRepository.save(review);

    }

    public void deleteReviewByCustomerId(int cid) {

        reviewRepository.deleteReviewByCustomerId(cid);
    }

}