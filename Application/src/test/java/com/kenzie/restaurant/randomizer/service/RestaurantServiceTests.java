package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.RestaurantRepository;
import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static java.util.UUID.randomUUID;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RestaurantServiceTests {
    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;
    private ReviewService reviewService;

    @BeforeEach
    void setup() {
        restaurantRepository = mock(RestaurantRepository.class);
        reviewService = mock(ReviewService.class);
        restaurantService = new RestaurantService(restaurantRepository, reviewService);
    }

    @Test
    void canAddRestaurant() {
        //GIVEN
        UUID restaurantId = randomUUID();
        String name = "test";
        String category = "bad food";
        List<String> storeHours = new ArrayList<>();
        String hours = "6:30am";
        storeHours.add(hours);

        Restaurant restaurant = new Restaurant(restaurantId, name, category, storeHours);

        ArgumentCaptor<RestaurantRecord> restaurantRecordCaptor = ArgumentCaptor.forClass(RestaurantRecord.class);

        when(restaurantRepository.save(any(RestaurantRecord.class))).then(i -> i.getArgumentAt(0, RestaurantRecord.class));


        // When
        Restaurant returnedRestaurant = restaurantService.addNewRestaurant(restaurant);

        // Then
        verify(restaurantRepository).save(restaurantRecordCaptor.capture());
        RestaurantRecord restaurantRecord = restaurantRecordCaptor.getValue();

        Assertions.assertNotNull(restaurantRecord, "The object is saved");
        Assertions.assertEquals(restaurantRecord.getRestaurantId(), restaurant.getRestaurantId(), "The id matches");
        Assertions.assertEquals(restaurantRecord.getName(), restaurant.getRestaurantName(), "The name matches");
        Assertions.assertEquals(restaurantRecord.getCategory(), restaurant.getCategory(), "The category matches");
        Assertions.assertEquals(restaurantRecord.getStoreHours(), restaurant.getStoreHours(), "The store hours match");

        Assertions.assertNotNull(returnedRestaurant, "The object is saved");
        Assertions.assertEquals(returnedRestaurant.getRestaurantId(), restaurant.getRestaurantId(), "The id matches");
        Assertions.assertEquals(returnedRestaurant.getRestaurantName(), restaurant.getRestaurantName(), "The name matches");
        Assertions.assertEquals(returnedRestaurant.getCategory(), restaurant.getCategory(), "The category matches");
        Assertions.assertEquals(returnedRestaurant.getStoreHours(), restaurant.getStoreHours(), "The store hours match");
    }

    @Test
    void getRandomRestaurant() {
        UUID restaurantId = randomUUID();
        String name = "test";
        String category = "bad food";
        List<String> storeHours = new ArrayList<>();
        String hours = "6:30am";
        storeHours.add(hours);

        UUID restaurantId2 = randomUUID();
        String name2 = "test2";
        String category2 = "good food";
        List<String> storeHours2 = new ArrayList<>();
        String hours2 = "6:30am";
        storeHours.add(hours2);

        String userId = "XxTESTMAN69xX";

        Restaurant restaurant = new Restaurant(restaurantId, name, category, storeHours);
        Restaurant restaurant2 = new Restaurant(restaurantId2, name2, category2, storeHours2);

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setStoreHours(restaurant.getStoreHours());
        restaurantRecord.setName(restaurant.getRestaurantName());
        restaurantRecord.setCategory(restaurant.getCategory());
        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());

        RestaurantRecord restaurantRecord2 = new RestaurantRecord();
        restaurantRecord2.setStoreHours(restaurant2.getStoreHours());
        restaurantRecord2.setName(restaurant2.getRestaurantName());
        restaurantRecord2.setCategory(restaurant2.getCategory());
        restaurantRecord2.setRestaurantId(restaurant2.getRestaurantId());

        List<RestaurantRecord> restaurantRecords = Arrays.asList(restaurantRecord, restaurantRecord2);

        Review review = new Review();
        review.setRestaurantId(restaurantId);
        review.setTitle("testMan");
        review.setUserId("XxTESTMAN69xX");
        review.setDescription("Test man likes test restaurant!");
        review.setRating(5);
        review.setPrice(10.0);

        when(restaurantRepository.findAll()).thenReturn(restaurantRecords);
        when(reviewService.findReview(restaurantId, userId)).thenReturn(review);
        when(reviewService.findReview(restaurantId2, userId)).thenReturn(review);

        // When
        Restaurant returnedRestaurant = restaurantService.getRandomRestaurant();
        List<Restaurant> restaurantList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            restaurantList.add(restaurantService.getRandomRestaurant());
        }

        // Then
        Assertions.assertTrue(restaurantList.contains(restaurant));
        Assertions.assertTrue(restaurantList.contains(restaurant2));
        if (returnedRestaurant.getRestaurantId().equals(restaurant.getRestaurantId())) {
            Assertions.assertNotNull(returnedRestaurant, "The object is saved");
            Assertions.assertEquals(returnedRestaurant.getRestaurantId(), restaurant.getRestaurantId(), "The id matches");
            Assertions.assertEquals(returnedRestaurant.getRestaurantName(), restaurant.getRestaurantName(), "The name matches");
            Assertions.assertEquals(returnedRestaurant.getCategory(), restaurant.getCategory(), "The category matches");
            Assertions.assertEquals(returnedRestaurant.getStoreHours(), restaurant.getStoreHours(), "The store hours match");
        } else {
            Assertions.assertNotNull(returnedRestaurant, "The object is saved");
            Assertions.assertEquals(returnedRestaurant.getRestaurantId(), restaurant2.getRestaurantId(), "The id matches");
            Assertions.assertEquals(returnedRestaurant.getRestaurantName(), restaurant2.getRestaurantName(), "The name matches");
            Assertions.assertEquals(returnedRestaurant.getCategory(), restaurant2.getCategory(), "The category matches");
            Assertions.assertEquals(returnedRestaurant.getStoreHours(), restaurant2.getStoreHours(), "The store hours match");
        }
    }

    @Test
    void findByRestaurantId() {
        UUID restaurantId = randomUUID();
        String name = "test";
        String category = "bad food";
        List<String> storeHours = new ArrayList<>();
        String hours = "6:30am";
        storeHours.add(hours);

        String userId = "XxTESTMAN69xX";

        Restaurant restaurant = new Restaurant(restaurantId, name, category, storeHours);

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setStoreHours(restaurant.getStoreHours());
        restaurantRecord.setName(restaurant.getRestaurantName());
        restaurantRecord.setCategory(restaurant.getCategory());
        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());


        Review review = new Review();
        review.setRestaurantId(restaurantId);
        review.setTitle("testMan");
        review.setUserId("XxTESTMAN69xX");
        review.setDescription("Test man likes test restaurant!");
        review.setRating(5);
        review.setPrice(10.0);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurantRecord));
        when(reviewService.findReview(restaurantId, userId)).thenReturn(review);

        // When
        Restaurant returnedRestaurant = restaurantService.findByRestaurantId(restaurantId);

        // Then
        Assertions.assertNotNull(returnedRestaurant, "The object is returned.");
        Assertions.assertEquals(returnedRestaurant.getRestaurantId(), restaurant.getRestaurantId(), "The id matches");
        Assertions.assertEquals(returnedRestaurant.getRestaurantName(), restaurant.getRestaurantName(), "The name matches");
        Assertions.assertEquals(returnedRestaurant.getCategory(), restaurant.getCategory(), "The category matches");
        Assertions.assertEquals(returnedRestaurant.getStoreHours(), restaurant.getStoreHours(), "The store hours match");
    }

    @Test
    void canAddRestaurant_RestaurantIsNull_ThrowsException() {

        //GIVEN // WHEN // THEN
        Assertions.assertThrows(RestaurantNotFoundException.class, () -> restaurantService.addNewRestaurant(null));

    }

    @Test
    void findByRestaurantId_RestaurantIdIsNull_ThrowsException() {
        //GIVEN
        UUID restaurantId = randomUUID();

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        //WHEN //THEN
        Assertions.assertThrows(RestaurantNotFoundException.class, () -> restaurantService.findByRestaurantId(restaurantId));
    }

    @Test
    void updateRestaurant_restaurantIsUpdated_returnNewRestaurant() {
        //GIVEN
        List<String> storeHours = new ArrayList<>();
        String hours = "6:30am";
        storeHours.add(hours);

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setStoreHours(storeHours);
        restaurantRecord.setName("bob");
        restaurantRecord.setCategory("burger");
        restaurantRecord.setRestaurantId(UUID.fromString(randomUUID().toString()));

        Restaurant restaurant = new Restaurant(
                restaurantRecord.getRestaurantId(),
                restaurantRecord.getName(),
                restaurantRecord.getCategory(),
                restaurantRecord.getStoreHours());


        ArgumentCaptor<RestaurantRecord> restaurantRecordArgumentCaptor = ArgumentCaptor.forClass(RestaurantRecord.class);
        // WHEN
        //restaurantService.updateRestaurant(restaurant);


        //THEN
        verify(restaurantRepository).save(restaurantRecordArgumentCaptor.capture());
        RestaurantRecord storedRestaurant = restaurantRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(restaurant);
        Assertions.assertEquals(storedRestaurant.getRestaurantId(), restaurant.getRestaurantId(), "The concert id matches");
        Assertions.assertEquals(storedRestaurant.getName(), restaurant.getRestaurantName(), "The reservation date matches");
        Assertions.assertEquals(storedRestaurant.getCategory(), restaurant.getCategory(), "The reservationClosed matches");
        Assertions.assertEquals(storedRestaurant.getStoreHours(), restaurant.getStoreHours(), "The ticketPurchased matches");
    }

    @Test
    void updateRestaurant_restaurantIsnull_throwsException() {
        //GIVEN
//        UUID restaurantId = null;
//        String name = null;
//        String category = "bad food";
//        String[] storeHours = new String[]{"Monday - Friday: 9-5", "Weekend: 12-6"};


        // WHEN //THEN
        Assertions.assertThrows(IllegalArgumentException.class, () -> restaurantService.updateRestaurant(null));
    }

}
