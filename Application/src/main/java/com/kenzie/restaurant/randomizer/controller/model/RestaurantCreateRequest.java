package com.kenzie.restaurant.randomizer.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RestaurantCreateRequest {


    @JsonProperty("name")
    private String name;

    @JsonProperty("category")
    private String category;

    @JsonProperty("storeHours")
    private List<String> storeHours;


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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantCreateRequest that = (RestaurantCreateRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash( name);
    }
}
