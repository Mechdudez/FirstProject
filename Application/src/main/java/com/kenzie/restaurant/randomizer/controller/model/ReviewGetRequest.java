package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

public class ReviewGetRequest {

    @NotEmpty
    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @JsonProperty("userId")
    private String userId;


    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


}
