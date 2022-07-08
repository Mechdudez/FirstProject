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
import java.util.UUID;

@Service
public class ReviewService {

    //private RestaurantService restaurantService;

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        //this.restaurantService = restaurantService;
        this.reviewRepository = reviewRepository;
    }

    public Review findReview(UUID restaurantId, String userId) {

        if (restaurantId == null || userId == null){
            throw new IllegalArgumentException();
        }

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

    public List<Review> findAllReviewsForRestaurant(UUID restaurantId) {
        List<Review> reviews = getAllReviews();

        List<Review> reviewsForStore = new ArrayList<>();

        for (Review review : reviews) {
            if (review.getRestaurantId().equals(restaurantId)) {
                reviewsForStore.add(review);
            }
        }

        return reviewsForStore;

    }

    public Review addNewReview( Review review) {

        if (review == null) {
            throw new ReviewNotFoundException("No review found by id!");

        }



        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setRestaurantName(review.getRestaurantName());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setTitle(review.getTitle());
        reviewRecord.setDescription(review.getDescription());
        reviewRepository.save(reviewRecord);

        return review;
    }

    public List<Review> getAllUserReviews(String userId) {
        List<Review> reviews = getAllReviews();

        if(reviews.isEmpty()){
            throw new ReviewNotFoundException("getallReviews failed");
        }

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

    public List<Review> getAllReviews(){
        List<Review> reviews = new ArrayList<>();
        reviewRepository
                .findAll()
                .forEach(review -> reviews.add((new Review(review.getRestaurantId(), review.getRestaurantName(), review.getUserId(), review.getRating(), review.getPrice(), review.getTitle(), review.getDescription()))));
        return reviews;
    }

    public void updateReview(Review review) {
        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setRestaurantName(review.getRestaurantName());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setTitle(review.getTitle());
        reviewRecord.setDescription(review.getDescription());

        reviewRepository.save(reviewRecord);
    }


}