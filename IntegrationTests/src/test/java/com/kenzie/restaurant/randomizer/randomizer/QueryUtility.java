package com.kenzie.restaurant.randomizer.randomizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.ReviewCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class QueryUtility {

    public ReviewControllerClient reviewControllerClient;

    public RestaurantControllerClient restaurantControllerClient;

    private final MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();


    public QueryUtility(MockMvc mvc) {
        this.mvc = mvc;
        this.reviewControllerClient = new ReviewControllerClient();
        this.restaurantControllerClient = new RestaurantControllerClient();

    }

    public class ReviewControllerClient {
        public ResultActions createReview(ReviewCreateRequest reviewCreateRequest) throws Exception {
            return mvc.perform(post("/review/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(reviewCreateRequest)));
        }
    }

    public class RestaurantControllerClient {

        public ResultActions createRestaurant(RestaurantCreateRequest restaurantCreateRequest) throws Exception {
            return mvc.perform(post("/restaurants/")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(restaurantCreateRequest)));
        }
        public ResultActions getRandomRestaurant() throws Exception {
            return mvc.perform(get("/restaurants/")
                    .accept(MediaType.APPLICATION_JSON));
        }
    }
}