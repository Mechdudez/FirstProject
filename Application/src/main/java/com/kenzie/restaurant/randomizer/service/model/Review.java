package com.kenzie.restaurant.randomizer.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;


public class Review {

    private String restaurantId;

    private String userName;

    private int rating;

    private Double price;

    private String description;


    public Review(String restaurantId, String userName, int rating,  Double price, String description) {
        this.restaurantId = restaurantId;
        this.userName = userName;
        this.rating = rating;
        this.price = price;
        this.description = description;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getUserId() {
        return userName;
    }

    public void setUserId(String userName) {
        this.userName = userName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return Objects.equals(restaurantId, review.restaurantId) && Objects.equals(userName, review.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, userName);
    }
}
