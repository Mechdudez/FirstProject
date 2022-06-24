package com.kenzie.restaurant.randomizer.controller;

import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantResponse;
import com.kenzie.restaurant.randomizer.controller.model.ReviewCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.ReviewResponse;
import com.kenzie.restaurant.randomizer.service.RestaurantService;
import com.kenzie.restaurant.randomizer.service.ReviewService;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

public class ReviewController {
    private ReviewService reviewService;

    @GetMapping("/{restaurantId}") //TODO please correct this path
    public ResponseEntity<ReviewResponse> findReviewByUserId(@PathVariable("restaurantId") String restaurantId, String userId) {
        Review review = reviewService.findReviewByUserId(restaurantId, userId);

        if (review == null) {
            return ResponseEntity.notFound().build();
        }

        ReviewResponse reviewResponse = createReviewResponse(review);
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();

        if (reviews == null || reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ReviewResponse> reviewResponses = new ArrayList<>();
        for (Review review : reviews) {
            reviewResponses.add(this.createReviewResponse(review));
        }

        return ResponseEntity.ok(reviewResponses);
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> addNewReview(@RequestBody ReviewCreateRequest reviewCreateRequest) {
        Review review = new Review(
                reviewCreateRequest.getRestaurantId(),
                reviewCreateRequest.getUserId(),
                reviewCreateRequest.getDescription());
        reviewService.addNewReview(reviewCreateRequest.getUserId(), review);

        RestaurantResponse reviewResponse = createRestaurantResponse(review);

        return ResponseEntity.created(URI.create("/reviews/" + reviewResponse.getRestaurantId())).body(reviewResponse);
    }

    private RestaurantResponse createRestaurantResponse(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse.setName(restaurant.getName());

        return restaurantResponse;
    }

    //TODO implement after MVP viable
//    @PutMapping
//    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
//        Restaurant restaurant = new Restaurant(restaurantUpdateRequest.getRestaurantId(),
//                restaurantUpdateRequest.getName(),
//                restaurantUpdateRequest.getCategory(),
//                restaurantUpdateRequest.getStoreHours());
//        reviewService.updateRestaurant(restaurant);
//
//        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);
//
//        return ResponseEntity.ok(restaurantResponse);
//    }
//
//    @DeleteMapping("/{restaurantId}")
//    public ResponseEntity deleteRestaurantById(@PathVariable("restaurantId") String restaurantId) {
//        reviewService.deleteRestaurant(restaurantId);
//        return ResponseEntity.noContent().build();
//    }
}
