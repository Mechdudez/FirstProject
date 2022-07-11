package com.kenzie.restaurant.randomizer.controller;


import com.google.gson.JsonObject;
import com.kenzie.restaurant.randomizer.controller.model.*;
import com.kenzie.restaurant.randomizer.service.RestaurantService;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import com.kenzie.restaurant.randomizer.service.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    // Pulls from Service
    private RestaurantService restaurantService;

    @Autowired
    RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }
    RestaurantController(){

    }


    //TODO implement later
//    @PostMapping("/all")
//    public  ResponseEntity<List<RestaurantResponse>> getReadData() throws IOException {
//        List<Restaurant> restaurantList = sampleRestaurantGenerator.readData();
//
//        if (restaurantList == null) {
//            return ResponseEntity.notFound().build();
//        }
//        List<RestaurantResponse> response = new ArrayList<>();
//        for (Restaurant restaurant : restaurantList) {
//            response.add(this.createRestaurantResponse(restaurant));
//        }
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantResponse> findByRestaurantId(@PathVariable("restaurantId") UUID restaurantId) {
        Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);

        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);
        return ResponseEntity.ok(restaurantResponse);
    }


    @GetMapping("/within{price}{category}")
    public ResponseEntity<RestaurantResponse> getSortedRestaurant(@PathVariable("price") Double price, @PathVariable("category") String category) {
        Restaurant restaurant = restaurantService.getSortedRestaurant(price, category);

        if (restaurant == null) {
            return ResponseEntity.noContent().build();
        }

        RestaurantResponse response = createRestaurantResponse(restaurant);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        if (restaurants == null || restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RestaurantResponse> response = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            response.add(this.createRestaurantResponse(restaurant));
        }

        return ResponseEntity.ok(response);
    }


    @GetMapping("/random")
    public ResponseEntity<RestaurantResponse> getRandomRestaurant() {
        Restaurant restaurant = restaurantService.getRandomRestaurant();

        if (restaurant == null) {
            return ResponseEntity.noContent().build();
        }

        RestaurantResponse response = createRestaurantResponse(restaurant);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> addNewRestaurant(@RequestBody RestaurantCreateRequest restaurantCreateRequest) {
        Restaurant restaurant = new Restaurant(UUID.randomUUID(),
                restaurantCreateRequest.getName(),
                restaurantCreateRequest.getCategory(),
                restaurantCreateRequest.getStoreHours());
        restaurantService.addNewRestaurant(restaurant);

        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);

        return ResponseEntity.created(URI.create("/restaurant/" + restaurantResponse.getRestaurantId())).body(restaurantResponse);
    }

    private RestaurantResponse createRestaurantResponse(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse.setCategory(restaurant.getCategory());
        restaurantResponse.setStoreHours(restaurant.getStoreHours());

        if (restaurant.getReviews() != null) {
            restaurantResponse.setReviews(restaurant.getReviews());
        }

        if (restaurant.getAveragePrice() != null) {
            restaurantResponse.setAveragePrice(restaurant.getAveragePrice());
        }

        if (restaurant.getAverageRating() != null) {
            restaurantResponse.setAverageRating(restaurant.getAverageRating());
        }

        return restaurantResponse;
    }

}
