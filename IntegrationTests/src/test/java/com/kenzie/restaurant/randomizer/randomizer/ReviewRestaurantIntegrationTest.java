package com.kenzie.restaurant.randomizer.randomizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.kenzie.restaurant.randomizer.IntegrationTest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.ReviewCreateRequest;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import java.util.Collections;
import java.util.UUID;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewRestaurantIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private QueryUtility queryUtility;

    @BeforeEach
    public void setup() {
        queryUtility = new QueryUtility(mvc);
    }

    // Happy Case
//    @Test
//    public void reviewRestaurant_validReview_reviewIsCreated() throws Exception {
//        String userId = mockNeat.strings().get();
//        String restaurant = mockNeat.strings().get();
//        Double price = mockNeat.doubles().get();
//        Integer rating = mockNeat.ints().get();
//        String description = mockNeat.strings().get();
//
//
//
//        String category = mockNeat.strings().get();
//        String[] storeHours = new String[]{mockNeat.strings().get()};
//
//        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
//        restaurantCreateRequest.setName(restaurant);
//        restaurantCreateRequest.setCategory(category);
//        restaurantCreateRequest.setStoreHours(storeHours);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        mvc.perform(post("/restaurant")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(restaurantCreateRequest)))
//                .andExpect(jsonPath("restaurant")
//                        .exists())
//                .andExpect(jsonPath("restaurant")
//                        .value(is(userId)))
//                .andExpect(jsonPath("category")
//                        .value(is(category)))
//                .andExpect(jsonPath("storehours")
//                        .value(is(storeHours)))
//                .andExpect(status().isCreated());
//
//
//        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
//        reviewCreateRequest.setUserId(userId);
//        reviewCreateRequest.setRestaurantName(restaurant);
//        reviewCreateRequest.setPrice(price);
//        reviewCreateRequest.setRating(rating);
//        reviewCreateRequest.setDescription(description);
//
//        mapper.registerModule(new JavaTimeModule());
//
//
//        mvc.perform(post("/review")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(reviewCreateRequest)))
//                .andExpect(jsonPath("review")
//                        .exists())
//                .andExpect(jsonPath("userId")
//                        .value(is(userId)))
//                .andExpect(jsonPath("restaurant")
//                        .value(is(restaurant)))
//                .andExpect(jsonPath("price")
//                        .value(is(price)))
//                .andExpect(jsonPath("rating")
//                        .value(is(rating)))
//                .andExpect(jsonPath("description")
//                        .value(is(description)))
//                .andExpect(status().isCreated());
//    }

    @Test
    public void reviewRestaurant_EmptyReview_throwException() throws Exception {
        //GIVEN
        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(UUID.randomUUID());
        reviewCreateRequest.setUserId("");
        reviewCreateRequest.setRestaurantName("");
        reviewCreateRequest.setPrice(0.0);
        reviewCreateRequest.setRating(0);
        reviewCreateRequest.setDescription("");


        //WHEN/THEN
        Assertions.assertThrows(NestedServletException.class, () -> {
            queryUtility.reviewControllerClient.createReview(reviewCreateRequest);
        });
    }

    @Test
    public void reviewRestaurant_NullRestaurant_ReturnBadRequest() throws Exception {
        //GIVEN

        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(null);
        reviewCreateRequest.setUserId(mockNeat.strings().get());
        reviewCreateRequest.setRestaurantName(mockNeat.strings().get());
        reviewCreateRequest.setPrice(mockNeat.doubles().get());
        reviewCreateRequest.setRating(mockNeat.ints().get());
        reviewCreateRequest.setDescription(mockNeat.strings().get());

        //WHEN //THEN

        Assertions.assertThrows(NestedServletException.class, () -> queryUtility.reviewControllerClient.createReview(reviewCreateRequest));
    }

}
