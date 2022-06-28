package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kenzie.restaurant.randomizer.service.model.Review;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantResponse {

    @JsonProperty("restaurantId")
    private String restaurantId;

    @JsonProperty("restaurantName")
    private String restaurantName;

    @JsonProperty("category")
    private String category;

    @JsonProperty("storeHours")
    private List<String> storeHours;

    @JsonProperty("averagePrice")
    private Double averagePrice;

    @JsonProperty("averageRating")
    private Double averageRating;

    @JsonProperty("reviews")
    private List<Review> reviews;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getStoreHours() {
        return storeHours;
    }

    public void setStoreHours(List<String> storeHours) {
        this.storeHours = storeHours;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}