package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.ReviewRepository;
import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.*;


public class ReviewServiceTests {
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
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";
        Review review = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId, rating, price, title, description);
        ArgumentCaptor<ReviewRecord> reviewRecordArgumentCaptor = ArgumentCaptor.forClass(ReviewRecord.class);

        when(reviewRepository.save(any(ReviewRecord.class))).then(i -> i.getArgumentAt(0, ReviewRecord.class));

        // WHEN
        Review returnedReview = reviewService.addNewReview(review);

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
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob1";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";

        Review review = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId, rating, price, title, description);

        String userId2 = "Bob2";
        Double price2 = 20.0;
        int rating2 = 3;
        String title2 = "Cant get worse";
        String description2 = "What a terrible place";

        Review review2 = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId2, rating2, price2, title2, description2);

        String userId3 = "Bob3";
        Double price3 = 20.0;
        int rating3 = 3;
        String title3 = "Somehow got worse";
        String description3 = "What a terrible place";

        Review review3 = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId3, rating3, price3, title3, description3);

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setTitle(review.getTitle());
        reviewRecord.setDescription(review.getDescription());

        ReviewRecord reviewRecord2 = new ReviewRecord();
        reviewRecord2.setRestaurantId(review2.getRestaurantId());
        reviewRecord2.setUserId(review2.getUserId());
        reviewRecord2.setPrice(review2.getPrice());
        reviewRecord2.setRating(review2.getRating());
        reviewRecord2.setTitle(review2.getTitle());
        reviewRecord2.setDescription(review2.getDescription());

        ReviewRecord reviewRecord3 = new ReviewRecord();
        reviewRecord3.setRestaurantId(review3.getRestaurantId());
        reviewRecord3.setUserId(review3.getUserId());
        reviewRecord3.setPrice(review3.getPrice());
        reviewRecord3.setRating(review3.getRating());
        reviewRecord3.setTitle(review3.getTitle());
        reviewRecord3.setDescription(review3.getDescription());

        List<ReviewRecord> records = Arrays.asList(reviewRecord, reviewRecord2, reviewRecord3);

        when(reviewRepository.findAll()).thenReturn(records);

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
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";

        Review review = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId, rating, price, title, description);

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
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";

        Review review = new Review(UUID.randomUUID(), restaurantId, restaurantName, userId, rating, price, title, description);

        UUID restaurantId2 = UUID.randomUUID();
        String restaurantName2 = "Bobbies Bistro";
        String userId2 = "Bob";
        Double price2 = 20.0;
        int rating2 = 3;
        String title2 = "Absolutely amazing";
        String description2 = "What a terrible place";

        Review review2 = new Review(UUID.randomUUID(), restaurantId2, restaurantName2, userId2, rating2, price2, title2, description2);

        UUID restaurantId3 = UUID.randomUUID();
        String restaurantName3 = "Bobbies Bistro";
        String userId3 = "Batman";
        Double price3 = 20.0;
        int rating3 = 3;
        String title3 = "Absolutely Awful";
        String description3 = "What a terrible place";

        Review review3 = new Review(UUID.randomUUID(), restaurantId3, restaurantName3, userId3, rating3, price3, title3, description3);

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantId(review.getRestaurantId());
        reviewRecord.setRestaurantName(review.getRestaurantName());
        reviewRecord.setUserId(review.getUserId());
        reviewRecord.setPrice(review.getPrice());
        reviewRecord.setRating(review.getRating());
        reviewRecord.setTitle(review.getTitle());
        reviewRecord.setDescription(review.getDescription());

        ReviewRecord reviewRecord2 = new ReviewRecord();
        reviewRecord2.setRestaurantId(review2.getRestaurantId());
        reviewRecord2.setRestaurantName(review2.getRestaurantName());
        reviewRecord2.setUserId(review2.getUserId());
        reviewRecord2.setPrice(review2.getPrice());
        reviewRecord2.setRating(review2.getRating());
        reviewRecord2.setTitle(review2.getTitle());
        reviewRecord2.setDescription(review2.getDescription());

        ReviewRecord reviewRecord3 = new ReviewRecord();
        reviewRecord3.setRestaurantId(review3.getRestaurantId());
        reviewRecord3.setRestaurantName(review3.getRestaurantName());
        reviewRecord3.setUserId(review3.getUserId());
        reviewRecord3.setPrice(review3.getPrice());
        reviewRecord3.setRating(review3.getRating());
        reviewRecord3.setTitle(review3.getTitle());
        reviewRecord3.setDescription(review3.getDescription());

        List<ReviewRecord> records = Arrays.asList(reviewRecord, reviewRecord2, reviewRecord3);

        when(reviewRepository.findAll()).thenReturn((records));

        // When
        List<Review> returnedReviewList = reviewService.getAllUserReviews(userId);

        // Then
        Assertions.assertTrue(returnedReviewList.contains(review));
        Assertions.assertTrue(returnedReviewList.contains(review2));

        if (reviewRecord.getUserId().equals(review.getUserId())) {
            Assertions.assertNotNull(review, "The object is returned.");
            Assertions.assertEquals(reviewRecord.getRestaurantId(), review.getRestaurantId(), "The restaurantId matches");
            Assertions.assertEquals(reviewRecord.getUserId(), review.getUserId(), "The userId matches");
            Assertions.assertEquals(reviewRecord.getPrice(), review.getPrice(), "The price matches");
            Assertions.assertEquals(reviewRecord.getRating(), review.getRating(), "The rating matches");
            Assertions.assertEquals(reviewRecord.getDescription(), review.getDescription(), "The description matches");
        } else {

        }

    }

    @Test
    void findReview_ReviewIsNull_ThrowException() {
        // GIVEN
        UUID restaurantId = null;
        String userId = null;


        // WHEN//THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            reviewService.findReview(restaurantId, userId);
        });

    }

    @Test
    void updateReview_reviewIsUpdated_returnsNewUpdatedReview() {
        // GIVEN
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantName(restaurantName);
        reviewRecord.setTitle(title);
        reviewRecord.setRestaurantId(restaurantId);
        reviewRecord.setUserId(userId);
        reviewRecord.setPrice(price);
        reviewRecord.setRating(rating);
        reviewRecord.setDescription(description);

        Review review = new Review(UUID.randomUUID(),
                reviewRecord.getRestaurantId(),
                reviewRecord.getRestaurantName(),
                reviewRecord.getUserId(),
                reviewRecord.getRating(),
                reviewRecord.getPrice(),
                reviewRecord.getTitle(),
                reviewRecord.getDescription());

        ArgumentCaptor<ReviewRecord> reviewRecordArgumentCaptor = ArgumentCaptor.forClass(ReviewRecord.class);


        // WHEN
        reviewService.updateReview(review);

        //THEN
        verify(reviewRepository).save(reviewRecordArgumentCaptor.capture());
        ReviewRecord storedReview = reviewRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(review);
        Assertions.assertEquals(storedReview.getRestaurantId(), review.getRestaurantId(), "The concert id matches");
        Assertions.assertEquals(storedReview.getRestaurantName(), review.getRestaurantName(), "The reservation date matches");
        Assertions.assertEquals(storedReview.getUserId(), review.getUserId(), "The reservationClosed matches");
        Assertions.assertEquals(storedReview.getRating(), review.getRating(), "The ticketPurchased matches");
        Assertions.assertEquals(storedReview.getTitle(), review.getTitle(), "The reservation closed date matches");
        Assertions.assertEquals(storedReview.getDescription(), review.getDescription(), "The reservation closed date matches");
    }

    @Test
    void getAverageRatingAndPriceForRestaurant_validRestaurant_ReturnRatingAndPrice() {
        //GIVEN
        UUID restaurantId = UUID.randomUUID();
        String restaurantName = "Bobbies Bistro";
        String userId = "Bob";
        Double price = 20.0;
        int rating = 3;
        String title = "Absolutely Awful";
        String description = "What a terrible place";

        ReviewRecord reviewRecord = new ReviewRecord();
        reviewRecord.setRestaurantName(restaurantName);
        reviewRecord.setTitle(title);
        reviewRecord.setRestaurantId(restaurantId);
        reviewRecord.setUserId(userId);
        reviewRecord.setPrice(price);
        reviewRecord.setRating(rating);
        reviewRecord.setDescription(description);

        String restaurantName2 = "Bobbies Bistro";
        String userId2 = "Bobby";
        Double price2 = 20.0;
        int rating2 = 3;
        String title2 = "Absolutely Awful";
        String description2 = "What a terrible place";

        ReviewRecord reviewRecord2 = new ReviewRecord();
        reviewRecord2.setRestaurantName(restaurantName2);
        reviewRecord2.setTitle(title2);
        reviewRecord2.setRestaurantId(restaurantId);
        reviewRecord2.setUserId(userId2);
        reviewRecord2.setPrice(price2);
        reviewRecord2.setRating(rating2);
        reviewRecord2.setDescription(description2);

        List<ReviewRecord> records = new ArrayList<>();
        records.add(reviewRecord);
        records.add(reviewRecord2);

        //WHEN
        when(reviewRepository.findAll()).thenReturn(records);

        //THEN
        Restaurant restaurant = reviewService.getAverageRatingAndPriceForRestaurant(restaurantId);

        Assertions.assertEquals(restaurant.getAverageRating(), (rating + rating2) / records.size());
        Assertions.assertEquals(restaurant.getAveragePrice(), (price + price2) / records.size());


    }

}
