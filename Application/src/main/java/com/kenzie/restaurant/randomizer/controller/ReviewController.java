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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
@RestController
@RequestMapping("/review")
public class ReviewController {
    private ReviewService reviewService;

    ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    //TODO Fix this method of the controller
    @GetMapping("/{userId}")
    public ResponseEntity<List<ReviewResponse>> getAllUserReviews(@PathVariable("userId") String userId) {
        System.out.println(userId);
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

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ReviewResponse> findReview(@PathVariable("restaurantId") String restaurantId, String userId) {
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

        return ResponseEntity.created(URI.create("/reviews/" + reviewResponse.getRestaurantId())).body(reviewResponse);
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
