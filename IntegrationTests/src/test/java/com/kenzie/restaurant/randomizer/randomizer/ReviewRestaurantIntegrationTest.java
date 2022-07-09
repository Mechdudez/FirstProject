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
    @Test
    public void reviewRestaurant_validReview_reviewIsCreated() throws Exception {
// GIVEN
        RestaurantCreateRequest restaurantCreateRequest = new RestaurantCreateRequest();
        restaurantCreateRequest.setName(mockNeat.strings().get());
        restaurantCreateRequest.setCategory(mockNeat.strings().get());
        restaurantCreateRequest.setStoreHours(new String[]{mockNeat.strings().get()});

        ResultActions result = queryUtility.restaurantControllerClient.createRestaurant(restaurantCreateRequest);

        Gson gson = new Gson();
        Restaurant restaurant = gson.fromJson(result.andReturn().getResponse().getContentAsString(), Restaurant.class);

        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest();
        reviewCreateRequest.setRestaurantId(UUID.fromString(mockNeat.strings().get()));
        reviewCreateRequest.setRestaurantId(restaurant.getRestaurantId());
        reviewCreateRequest.setUserId(mockNeat.strings().get());
        reviewCreateRequest.setRestaurantName(mockNeat.strings().get());
        reviewCreateRequest.setPrice(mockNeat.doubles().get());
        reviewCreateRequest.setRating(mockNeat.ints().get());
        reviewCreateRequest.setDescription(mockNeat.strings().get());

        // WHEN

        queryUtility.reviewControllerClient.createReview(reviewCreateRequest)
                //THEN
                .andExpect(status().isCreated());
    }

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
