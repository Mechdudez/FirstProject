package com.kenzie.restaurant.randomizer.controller;

import com.kenzie.restaurant.randomizer.controller.model.*;
import com.kenzie.restaurant.randomizer.service.RestaurantService;
import com.kenzie.restaurant.randomizer.service.ReviewService;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.UUID.randomUUID;
@RestController
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    //ToDo Fix this method of the controller
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> getAllUserReviews(@PathVariable String userId) {
        List<Review> reviews = reviewService.getAllUserReviews(userId);

        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponses.add(createReviewResponse(review));
        }

        return ResponseEntity.ok(reviewResponses);
    }

    @GetMapping("/restaurant/{restaurantId}{userId}")
    public ResponseEntity<ReviewResponse> findReview(@PathVariable String restaurantId, @PathVariable String userId) {
        Review review = reviewService.findReview(restaurantId, userId);

        if (review == null) {
            return ResponseEntity.notFound().build();
        }

        ReviewResponse reviewResponse = createReviewResponse(review);
        return ResponseEntity.ok(reviewResponse);
    }



    @PostMapping
    public ResponseEntity<ReviewResponse> addNewReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        Review review = new Review(
                reviewCreateRequest.getRestaurantId(),
                reviewCreateRequest.getRestaurantName(),
                reviewCreateRequest.getUserId(),
                reviewCreateRequest.getRating(),
                reviewCreateRequest.getPrice(),
                reviewCreateRequest.getTitle(),
                reviewCreateRequest.getDescription());
        reviewService.addNewReview(review);

        ReviewResponse reviewResponse = createReviewResponse(review);

        return ResponseEntity.created(URI.create("/review/" + reviewResponse.getRestaurantId())).body(reviewResponse);
    }

    private ReviewResponse createReviewResponse(Review review) {
        ReviewResponse reviewResponse = new ReviewResponse();
        reviewResponse.setRestaurantId(review.getRestaurantId());
        reviewResponse.setRestaurantName(review.getRestaurantName());
        reviewResponse.setUserId(review.getUserId());
        reviewResponse.setPrice(review.getPrice());
        reviewResponse.setRating(review.getRating());
        reviewResponse.setTitle(review.getTitle());
        reviewResponse.setDescription(review.getDescription());


        return reviewResponse;
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponse>> findAllReviewsForRestaurant(@PathVariable String restaurantId){

        List<Review> reviewList = reviewService.findAllReviewsForRestaurant(restaurantId);

        if(reviewList.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        List<ReviewResponse> reviewResponses = reviewList.stream().map(this::createReviewResponse).collect(Collectors.toList());

        return ResponseEntity.ok(reviewResponses);
    }


    //TODO implement after MVP viable
    @PutMapping
    public ResponseEntity<ReviewResponse> updateReview(@RequestBody ReviewUpdateRequest reviewUpdateRequest) {
        Review review = new Review(reviewUpdateRequest.getRestaurantId(),
                reviewUpdateRequest.getRestaurantName(),
                reviewUpdateRequest.getUserId(),
                reviewUpdateRequest.getRating(),
                reviewUpdateRequest.getPrice(),
                reviewUpdateRequest.getTitle(),
                reviewUpdateRequest.getDescription());
        reviewService.updateReview(review);

        ReviewResponse reviewResponse = createReviewResponse(review);

        return ResponseEntity.ok(reviewResponse);
    }

//
//    @DeleteMapping("/{restaurantId}")
//    public ResponseEntity deleteRestaurantById(@PathVariable("restaurantId") String restaurantId) {
//        reviewService.deleteRestaurant(restaurantId);
//        return ResponseEntity.noContent().build();
//    }
}
