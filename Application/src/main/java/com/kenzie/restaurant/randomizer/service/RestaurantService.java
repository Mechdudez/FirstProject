package com.kenzie.restaurant.randomizer.service;

import com.kenzie.restaurant.randomizer.repositories.RestaurantRepository;
import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final ReviewService reviewService;



    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, ReviewService reviewService){
        this.restaurantRepository = restaurantRepository;
        this.reviewService = reviewService;
    }




    //TODO implement later
//    public List<Restaurant> getReadDate(List<Restaurant> restaurantList) throws IOException{
//
//
//        restaurantList = sampleRestaurantGenerator.readData();
//
//        if(restaurantList == null){
//            throw new RestaurantNotFoundException("Cannot find a restaurant");
//        }
//        RestaurantRecord restaurantRecord = new RestaurantRecord();
//        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());
//        restaurantRecord.setAveragePrice(restaurant.getAveragePrice());
//        restaurantRecord.setAverageRating(restaurant.getAverageRating());
//        restaurantRecord.setName(restaurant.getRestaurantName());
//        restaurantRecord.setCategory(restaurant.getCategory());
//
//        restaurantRepository.save(restaurantRecord);
//
//
//        return restaurantList;
//    }


    public Restaurant addNewRestaurant(Restaurant restaurant){
        if (restaurant == null){
            throw new RestaurantNotFoundException("Cannot add restaurant: restaurant is invalid");
        }

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());
        restaurantRecord.setName(restaurant.getRestaurantName());
        restaurantRecord.setCategory(restaurant.getCategory());
        restaurantRecord.setStoreHours(restaurant.getStoreHours());

        restaurantRepository.save(restaurantRecord);

        return restaurant;
    }


    public Restaurant findByRestaurantId(UUID restaurantId) {
        Restaurant restaurantFromBackend = restaurantRepository.findById(restaurantId)
                .map(restaurant -> new Restaurant(restaurant.getRestaurantId(), restaurant.getName(), restaurant.getCategory(), restaurant.getStoreHours()))
                .orElse(null);

        if (restaurantFromBackend == null){
            throw new RestaurantNotFoundException("No restaurant found by id!");
        }

        return setReviews(restaurantFromBackend);
    }

    public List<Restaurant> getAllRestaurants() {

        List<Restaurant> restaurantList = new ArrayList<>();

        restaurantRepository.findAll()
                .forEach(restaurant -> restaurantList.add(new Restaurant(restaurant.getRestaurantId(), restaurant.getName(), restaurant.getCategory(), restaurant.getStoreHours())));
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
        List<Restaurant> restaurantList = getAllRestaurants(); //TODO this is empty
        Random rand = new Random();
        if (restaurantList.isEmpty()) {
            throw new RestaurantNotFoundException("No restaurant in database.");
        } else {
            return setReviews(restaurantList.get(rand.nextInt(restaurantList.size())));
        }

    }

    public Restaurant getSortedRestaurant(Double price, String category){
        List<Restaurant> restaurantList = getAllRestaurants();
        List<Restaurant> sortedList = sortRestaurants(restaurantList, category, price);
        Random rand = new Random();
        return setReviews(sortedList.get(rand.nextInt(sortedList.size())));
    }

    public List<Restaurant> sortRestaurants(List<Restaurant> restaurantList, String category, Double price){

        List<Restaurant> sortedRestaurants = new ArrayList<>();
        for(Restaurant restaurant : restaurantList){
            if (restaurant.getAveragePrice().compareTo(price)<=0 && restaurant.getCategory().equals(category)){
                sortedRestaurants.add(restaurant);
            }
        }

        return sortedRestaurants;
    }

    public Restaurant updateRestaurant(UUID restaurantId){

        if (restaurantId == null){
            throw new IllegalArgumentException("No restaurant passed in");
        }

        Restaurant restaurant = reviewService.getAverageRatingAndPriceForRestaurant(restaurantId);

        RestaurantRecord restaurantRecord = new RestaurantRecord();
        restaurantRecord.setRestaurantId(restaurant.getRestaurantId());
        restaurantRecord.setName(restaurant.getRestaurantName());
        restaurantRecord.setCategory(restaurant.getCategory());
        restaurantRecord.setAverageRating(restaurant.getAverageRating());
        restaurantRecord.setAveragePrice(restaurant.getAveragePrice());

        restaurantRepository.save(restaurantRecord);

        return restaurant;
    }

}
