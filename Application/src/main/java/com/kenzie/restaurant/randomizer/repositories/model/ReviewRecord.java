package com.kenzie.restaurant.randomizer.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

import java.util.Objects;

public class ReviewRecord {
    private String restaurantId;

    private String restaurant;
    
    private String userId;

    private String title;

    private Double price;

    private int rating;
    
    private String description;


    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    @DynamoDBAttribute(attributeName = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double averagePrice) {
        this.price = averagePrice;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute(attributeName = "restaurant")
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    @DynamoDBAttribute(attributeName = "restaurantId")
    public String getRestaurantId() {
        return restaurant;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurant = restaurant;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewRecord that = (ReviewRecord) o;
        return Objects.equals(userId, that.userId) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, title);
    }
}
