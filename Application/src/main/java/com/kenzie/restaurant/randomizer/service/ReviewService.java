package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.controller.model.ReviewResponse;
import com.kenzie.restaurant.randomizer.repositories.ReviewRepository;
import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.ArrayList;
import java.util.List;


public class ReviewService {

    //private RestaurantService restaurantService;

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        //this.restaurantService = restaurantService;
        this.reviewRepository = reviewRepository;
    }

    public Review findReview(String restaurantId, String userId) {
        List<Review> reviews = findAllReviewsForRestaurant(restaurantId);
        if (reviews == null) {
            throw new ReviewNotFoundException("No review found by id!");
        }
        for (Review review : reviews) {
            if (review.getUserId().equals(userId)) {
                return review;
            }
        }
        throw new ReviewNotFoundException("No review found by id!");
    }

    public List<Review> findAllReviewsForRestaurant(String restaurantId) {
        List<Review> reviews = new ArrayList<>();
        reviewRepository
                .findAll()
                .forEach(review -> reviews.add((new Review(restaurantId, review.getUserId(), review.getRating(), review.getPrice(), review.getDescription()))));

        List<Review> reviewsForStore = new ArrayList<>();

        for (Review review : reviews) {
            if (review.getRestaurantId().equals(restaurantId)) {
                reviewsForStore.add(review);
            }
        }

        if (reviewsForStore.isEmpty()){
            throw new ReviewNotFoundException("No reviews found for store!");
        }

        return reviewsForStore;

    }

    public Review addNewReview(String reviewId, Review review) {

        if (reviewId == null || reviewId.isEmpty()) {
            throw new ReviewNotFoundException("No review found by id!");

        }

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurant(review.getRestaurantId());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setDescription(review.getDescription());
        reviewRepository.save(reviewRecord);

        return review;
    }

    public List<Review> getAllUserReviews(String userId) {
        List<Review> reviews = new ArrayList<>();
        reviewRepository
                .findAll()
                .forEach(review -> reviews.add((new Review(review.getRestaurantId(), review.getUserId(), review.getRating(), review.getPrice(), review.getDescription()))));

        List<Review> reviewsForUser = new ArrayList<>();

        for (Review review : reviews) {
            if (review.getUserId().equals(userId)) {
                reviewsForUser.add(review);
            }
        }

        if (reviewsForUser.isEmpty()){
            throw new ReviewNotFoundException("No reviews found for user!");
        }

        return reviewsForUser;
    }


}