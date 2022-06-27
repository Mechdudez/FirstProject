package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.ReviewRepository;
import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;


public class ReviewTests {
    private ReviewRepository reviewRepository;
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        reviewRepository = mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    void canAddAReview() {
        // GIVEN
        String restaurantId = "12345";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String description = "What a terrible place";
        Review review = new Review(restaurantId, userId, rating, price, description);
        ArgumentCaptor<ReviewRecord> reviewRecordArgumentCaptor = ArgumentCaptor.forClass(ReviewRecord.class);

        when(reviewRepository.save(any(ReviewRecord.class))).then(i -> i.getArgumentAt(0, ReviewRecord.class));

        // WHEN
        Review returnedReview = reviewService.addNewReview("Bob", review);

        // THEN
        verify(reviewRepository).save(reviewRecordArgumentCaptor.capture());
        ReviewRecord reviewRecord = reviewRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(reviewRecord, "The review was saved");
        Assertions.assertEquals(reviewRecord.getRestaurantId(), review.getRestaurantId(), "You did it! RestaurantId matches");
        Assertions.assertEquals(reviewRecord.getUserId(), review.getUserId(), "You did it! UserId matches");
        Assertions.assertEquals(reviewRecord.getPrice(), review.getPrice(), "You did it! Price matches");
        Assertions.assertEquals(reviewRecord.getRating(), review.getRating(), "You did it! Rating matches");
        Assertions.assertEquals(reviewRecord.getDescription(), review.getDescription(), "You did it! Description matches");

        Assertions.assertNotNull(returnedReview, "The review was saved");
        Assertions.assertEquals(returnedReview.getRestaurantId(), review.getRestaurantId(), "You did it! RestaurantId matches");
        Assertions.assertEquals(returnedReview.getUserId(), review.getUserId(), "You did it! UserId matches");
        Assertions.assertEquals(returnedReview.getPrice(), review.getPrice(), "You did it! Price matches");
        Assertions.assertEquals(returnedReview.getRating(), review.getRating(), "You did it! Rating matches");
        Assertions.assertEquals(returnedReview.getDescription(), review.getDescription(), "You did it! Description matches");


    }

    @Test
    void canFindAllReviewsForRestaurant() {
        // GIVEN
        String restaurantId = "testId";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String description = "What a terrible place";

        Review review = new Review(restaurantId, userId, rating, price, description);

        String restaurantId2 = "testId";
        String userId2 = "Bob";
        Double price2 = 20.0;
        int rating2 = 3;
        String description2 = "What a terrible place";

        Review review2 = new Review(restaurantId2, userId2, rating2, price2, description2);

        String restaurantId3 = "testId";
        String userId3 = "Bob";
        Double price3 = 20.0;
        int rating3 = 3;
        String description3 = "What a terrible place";

        Review review3 = new Review(restaurantId3, userId3, rating3, price3, description3);

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setDescription(review.getDescription());

        ReviewRecord reviewRecord2 = new ReviewRecord();
        reviewRecord.setRestaurantId(review2.getRestaurantId());
        reviewRecord.setUserId(review2.getUserId());
        reviewRecord.setPrice(review2.getPrice());
        reviewRecord.setRating(review2.getRating());
        reviewRecord.setDescription(review2.getDescription());

        ReviewRecord reviewRecord3 = new ReviewRecord();
        reviewRecord.setRestaurantId(review3.getRestaurantId());
        reviewRecord.setUserId(review3.getUserId());
        reviewRecord.setPrice(review3.getPrice());
        reviewRecord.setRating(review3.getRating());
        reviewRecord.setDescription(review3.getDescription());

        when(reviewRepository.findAll()).thenReturn(Arrays.asList(reviewRecord, reviewRecord2, reviewRecord3));

        // When
        List<Review> returnedReviewList = reviewService.findAllReviewsForRestaurant(restaurantId);

        // Then
        Assertions.assertTrue(returnedReviewList.contains(review));
        Assertions.assertTrue(returnedReviewList.contains(review2));
        Assertions.assertTrue(returnedReviewList.contains(review3));
    }

    @Test
    void canFindReview() {
        // GIVEN
        String restaurantId = "testId";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String description = "What a terrible place";

        Review review = new Review(restaurantId, userId, rating, price, description);

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setDescription(review.getDescription());

        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(reviewRecord));

        // When
        Review returnedReview = reviewService.findReview(restaurantId, userId);

        // Then
        Assertions.assertNotNull(returnedReview, "The object is returned.");
        Assertions.assertEquals(returnedReview.getRestaurantId(), review.getRestaurantId(), "The restaurantId matches");
        Assertions.assertEquals(returnedReview.getUserId(), review.getUserId(), "The userId matches");
        Assertions.assertEquals(returnedReview.getPrice(), review.getPrice(), "The price matches");
        Assertions.assertEquals(returnedReview.getRating(), review.getRating(), "The rating matches");
        Assertions.assertEquals(returnedReview.getDescription(), review.getDescription(), "The description matches");
    }

    @Test
    void getAllUserReviews() {
        // GIVEN

        // WHEN

        // THEN

    }


}
