package com.kenzie.restaurant.randomizer.service.model;

import java.util.List;
import java.util.Objects;

public class Restaurant {
    private String restaurantId;

    private String restaurantName;

    private String category;

    private List<String> storeHours;


//TODO implement these properties after MVP
    private Double averagePrice;

    private Double averageRating;

    private List<Review> reviews;

    public Restaurant(String restaurantId, String restaurantName, String category, List<String> storeHours) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.category = category;
        this.storeHours = storeHours;
    }

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

    public void setReviews(List<Review> reviews){
        this.reviews = reviews;
    }

    public List<Review> getReviews(){
        return reviews;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, restaurantName);
    }
}

