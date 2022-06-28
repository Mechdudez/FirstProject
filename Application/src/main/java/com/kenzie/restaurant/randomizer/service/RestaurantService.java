package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.RestaurantRepository;
import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final ReviewService reviewService;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, ReviewService reviewService){
        this.restaurantRepository = restaurantRepository;
        this.reviewService = reviewService;
    }

    public Restaurant addNewRestaurant(String userId, Restaurant restaurant){
        if (userId == null || userId.equals("")){
            throw new IllegalArgumentException("Cannot add restaurant: userId is invalid.");
        }

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setId(restaurant.getRestaurantId());
        restaurantRecord.setName(restaurant.getRestaurantName());
        restaurantRecord.setCategory(restaurant.getCategory());
        restaurantRecord.setStoreHours(restaurant.getStoreHours());

        restaurantRepository.save(restaurantRecord);

        return restaurant;
    }

    public Restaurant findByRestaurantId(String restaurantId) {
        Restaurant restaurantFromBackend = restaurantRepository.findById(restaurantId)
                .map(restaurant -> new Restaurant(restaurant.getId(), restaurant.getName(), restaurant.getCategory(), restaurant.getStoreHours()))
                .orElse(null);

        if (restaurantFromBackend == null){
            throw new RestaurantNotFoundException("No restaurant found by id!");
        }

        return setReviews(restaurantFromBackend);
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurantList = new ArrayList<>();
        restaurantRepository.findAll()
                .forEach(restaurant -> restaurantList.add(new Restaurant(restaurant.getId(), restaurant.getName(), restaurant.getCategory(), restaurant.getStoreHours())));
        return restaurantList;
    }

    public Restaurant setReviews(Restaurant restaurant){
        List<Review> reviews = reviewService.findAllReviewsForRestaurant(restaurant.getRestaurantId());
        if (reviews != null){
            restaurant.setReviews(reviews);
        }
        return restaurant;
    }

    public Restaurant getRandomRestaurant(){
        List<Restaurant> restaurantList = getAllRestaurants();
        Random rand = new Random();
        return setReviews(restaurantList.get(rand.nextInt(restaurantList.size())));
    }

    public List<Restaurant> sortRestaurants(List<Restaurant> restaurantList, String category, Double price){

        List<Restaurant> sortedRestaurants = new ArrayList<>();
        for(Restaurant restaurant : restaurantList){
            if (restaurant.getAveragePrice().compareTo(price)<=0 && restaurant.getCategory().equals(category)){
                sortedRestaurants.add(restaurant);
            }
        }

        return restaurantList;
    }

}
