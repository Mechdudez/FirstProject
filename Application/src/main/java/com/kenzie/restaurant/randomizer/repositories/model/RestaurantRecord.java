package com.kenzie.restaurant.randomizer.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@DynamoDBTable(tableName = "restaurants")
public class RestaurantRecord {
    private UUID restaurantId;

    private String name;

    private Double averagePrice;

    private Double averageRating;

    private String category;

    private List<String> storeHours;


    @DynamoDBHashKey(attributeName = "restaurantId")
    public UUID getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(UUID restaurantId) {
        this.restaurantId = restaurantId;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "averagePrice")
    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    @DynamoDBAttribute(attributeName = "averageRating")
    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    @DynamoDBAttribute(attributeName = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @DynamoDBAttribute(attributeName = "hours")
    public List<String> getStoreHours() {
        return storeHours;
    }

    public void setStoreHours(List<String> storeHours) {
        this.storeHours = storeHours;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantRecord)) return false;
        RestaurantRecord that = (RestaurantRecord) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, name);
    }
}

