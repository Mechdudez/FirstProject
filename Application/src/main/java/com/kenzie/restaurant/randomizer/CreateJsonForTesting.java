package com.kenzie.restaurant.randomizer;

import com.google.gson.Gson;
import com.kenzie.restaurant.randomizer.controller.model.RestaurantCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class CreateJsonForTesting {

    public static void main(String[] args){
        RestaurantCreateRequest request = new RestaurantCreateRequest();
        List<String> storeHours = new ArrayList<>();
        storeHours.add("Monday: 9-5");
        storeHours.add("Tuesday: 9-10");
        request.setStoreHours(storeHours);
        request.setName("Jeff");
        request.setCategory("Bad food");

        System.out.println(new Gson().toJson(request));

        // Json example to create a restaurant
        // {
        //    "restaurantId":"1",
        //    "userId":"73",
        //    "name":"HardlyFood",
        //    "category":"good food",
        //    "storeHours":[
        //        "Monday: 9-5",
        //        "Tuesday: 9-10"
        //    ]
        //}

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
