package com.kenzie.restaurant.randomizer;

import com.google.gson.Gson;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class CreateJsonForTesting {

    public static void main(String[] args){
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        List<String> hours = new ArrayList<>();
        hours.add("test1");
        hours.add("test2");
        request.setStoreHours(hours);
        request.setName("Jeff");
        request.setCategory("Bad food");

        List<String> test = new ArrayList<>();
        test.add("Monday : 90");
        test.add("Tuesday: 90");

        Gson gson = new Gson();

        System.out.println(gson.toJson(request));

        // Json example to create a restaurant
//         {
//            "name":"HardlyFood",
//            "category":"good food",
//            "storeHours":[
//                "Monday: 9-5",
//                "Tuesday: 9-10"
//            ]
//        }

        // Json example to create a review
//         {
//            "restaurantId":"1",
//            "restaurantName":"Bobbies Bistro",
//            "userId":"73",
//            "title":"Bad Food",
//            "description":"food was alright, staff was bad",
//            "rating":"2",
//            "price":"10.50"
//        }









    }


}
