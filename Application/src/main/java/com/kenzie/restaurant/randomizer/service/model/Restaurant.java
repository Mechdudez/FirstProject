package com.kenzie.restaurant.randomizer.service.model;

import java.util.List;
import java.util.Objects;

public class Restaurant {
    private String restaurantId;

    private String name;

    private String category;

    private List<String> storeHours;

    private List<Review> reviews;

    public Restaurant(String restaurantId, String name, String category, List<String> storeHours) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.category = category;
        this.storeHours = storeHours;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord)) return false;
        com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord that = (com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord) o;
        return Objects.equals(restaurantId, that.getId()) && Objects.equals(name, that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, name);
    }
}

