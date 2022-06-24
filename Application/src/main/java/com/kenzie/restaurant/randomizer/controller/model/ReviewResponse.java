package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {

    @JsonProperty("restaurantId")
    private String restaurantId;

    @JsonProperty("userId")
    private String userId;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() { return userId; }

    public void setUserId(String id) { this.userId = userId; }
}
