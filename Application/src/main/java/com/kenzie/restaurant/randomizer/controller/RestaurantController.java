package com.kenzie.restaurant.randomizer.controller;


import com.google.gson.JsonObject;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantGetRequest;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantResponse;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantUpdateRequest;
import com.kenzie.restaurant.randomizer.service.RestaurantService;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
 // Pulls from Service
    private RestaurantService restaurantService;

    RestaurantController(RestaurantService restaurantService){this.restaurantService = restaurantService;}

    @GetMapping("/find/{restaurantId}")
    public ResponseEntity<RestaurantResponse> searchRestaurantById(@PathVariable String restaurantId) {
        Restaurant restaurant = restaurantService.findByRestaurantId(restaurantId);

        if (restaurant == null) {
            return ResponseEntity.notFound().build();
        }

        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);
        return ResponseEntity.ok(restaurantResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantResponse>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        if (restaurants == null ||  restaurants.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<RestaurantResponse> response = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            response.add(this.createRestaurantResponse(restaurant));
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<RestaurantResponse> getRandomRestaurant() {
        Restaurant restaurant = restaurantService.getRandomRestaurant();

        if (restaurant == null) {
            return ResponseEntity.noContent().build();
        }

        RestaurantResponse response = createRestaurantResponse(restaurant);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/within/{price}{category}")
    public ResponseEntity<RestaurantResponse> getSortedRestaurant(@PathVariable Double price, @PathVariable String category) {
        Restaurant restaurant = restaurantService.getSortedRestaurant(price, category);

        if (restaurant == null) {
            return ResponseEntity.noContent().build();
        }

        RestaurantResponse response = createRestaurantResponse(restaurant);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RestaurantResponse> addNewRestaurant(@RequestBody RestaurantCreateRequest restaurantCreateRequest) {
        Restaurant restaurant = new Restaurant(randomUUID().toString(),
                restaurantCreateRequest.getName(),
                restaurantCreateRequest.getCategory(),
                restaurantCreateRequest.getStoreHours());
        restaurantService.addNewRestaurant(restaurantCreateRequest.getUserId(), restaurant);

        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);

        return ResponseEntity.created(URI.create("/restaurants/" + restaurantResponse.getRestaurantId())).body(restaurantResponse);
    }

    @PutMapping("/update")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
        Restaurant restaurant = new Restaurant(restaurantUpdateRequest.getRestaurantId(),
                restaurantUpdateRequest.getName(),
                restaurantUpdateRequest.getCategory(),
                restaurantUpdateRequest.getStoreHours());


        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurantService.updateRestaurant(restaurant));

        return ResponseEntity.created(URI.create("/restaurants/" + restaurantResponse.getRestaurantId())).body(restaurantResponse);
    }

    private RestaurantResponse createRestaurantResponse(Restaurant restaurant) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();
        restaurantResponse.setRestaurantId(restaurant.getRestaurantId());
        restaurantResponse.setRestaurantName(restaurant.getRestaurantName());
        restaurantResponse.setCategory(restaurant.getCategory());
        restaurantResponse.setStoreHours(restaurant.getStoreHours());

        if (restaurant.getReviews() != null){
            restaurantResponse.setReviews(restaurant.getReviews());
        }

        if (restaurant.getAveragePrice() != null){
            restaurantResponse.setAveragePrice(restaurant.getAveragePrice());
        }

        if (restaurant.getAverageRating() != null){
            restaurantResponse.setAverageRating(restaurant.getAverageRating());
        }

        return restaurantResponse;
    }

    //TODO implement after MVP viable
//    @PutMapping
//    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody RestaurantUpdateRequest restaurantUpdateRequest) {
//        Restaurant restaurant = new Restaurant(restaurantUpdateRequest.getRestaurantId(),
//                restaurantUpdateRequest.getName(),
//                restaurantUpdateRequest.getCategory(),
//                restaurantUpdateRequest.getStoreHours());
//        restaurantService.updateRestaurant(restaurant);
//
//        RestaurantResponse restaurantResponse = createRestaurantResponse(restaurant);
//
//        return ResponseEntity.ok(restaurantResponse);
//    }
//
//    @DeleteMapping("/{restaurantId}")
//    public ResponseEntity deleteRestaurantById(@PathVariable("restaurantId") String restaurantId) {
//        restaurantService.deleteRestaurant(restaurantId);
//        return ResponseEntity.noContent().build();
//    }


}
