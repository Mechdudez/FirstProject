package com.kenzie.restaurant.randomizer.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
import java.util.UUID;


public class Review {

    private UUID restaurantId;

    private String restaurantName;

    private String userId;

    private String title;

    private int rating;

    private Double price;

    private String description;


    public Review(UUID restaurantId, String restaurantName, String userId, int rating, Double price, String title, String description) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.userId = userId;
        this.rating = rating;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public Review(){}

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(restaurantId, review.restaurantId) && Objects.equals(userId, review.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, userId);
    }
}
