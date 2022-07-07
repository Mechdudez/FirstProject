package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponse {

    @JsonProperty("restaurantId")
    private UUID restaurantId;

    @NotEmpty
    @JsonProperty("restaurantName")
    private String restaurantName;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("description")
    private String description;

    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
