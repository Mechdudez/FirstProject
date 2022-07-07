package com.kenzie.restaurant.randomizer.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;
import java.util.UUID;

@DynamoDBTable(tableName = "reviews")
public class ReviewRecord {
    private UUID restaurantId;

    private String restaurantName;
    
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

    @DynamoDBAttribute(attributeName = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double averagePrice) {
        this.price = averagePrice;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public int getRating() {
        return rating;
    }
    
    public void setRating(int rating) {
        this.rating = rating;
    }

    @DynamoDBAttribute(attributeName = "restaurantName")
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @DynamoDBAttribute(attributeName = "restaurantId")
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
