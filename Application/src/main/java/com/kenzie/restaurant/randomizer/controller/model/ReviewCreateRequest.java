package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class ReviewCreateRequest {

    @NotEmpty
    @JsonProperty("restaurantId")
    private String restaurantId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("price")
    private Double price;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }
}
