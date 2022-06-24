package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.ReviewRepository;
import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private RestaurantService restaurantService;

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(RestaurantService restaurantService, ReviewRepository reviewRepository) {
        this.restaurantService = restaurantService;
        this.reviewRepository = reviewRepository;
    }

    public Review findByReviewId(String reviewId) {
        Review reviewBackend = reviewRepository
                .findById(reviewId)
                .map(review -> new Review(review.getRestaurant(), review.getUserId(), review.getRating(), review.getPrice(), review.getReview()))
                .orElse(null);

        if (reviewBackend == null){
            throw new ReviewNotFoundException("No review found by id!");
        }

        return reviewBackend;
    }

    public List<Review> findAllReviewsForRestaurant(String restaurantId) {
        List<Review> reviews = new ArrayList<>();
        reviewRepository
                .findAll()
                .forEach(review -> reviews.add((new Review(restaurantId, review.getUserId(), review.getRating(), review.getPrice(), review.getReview()))));

        List<Review> reviewsForStore = new ArrayList<>();

        for (Review review : reviews){
            if (review.getRestaurantId().equals(restaurantId)){
                reviewsForStore.add(review);
            }
        }
        return reviewsForStore;

    }
    public Review AddReview(String reviewId, Review review){

        if(reviewId == null || reviewId.isEmpty()){
            throw new ReviewNotFoundException();

        }
        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurant(review.getRestaurantId());
        reviewRecord.setUserId(review.getUserId());
       // reviewRecord.setName(review.getName);

        reviewRepository.save(reviewRecord);
        return review;
    }
}