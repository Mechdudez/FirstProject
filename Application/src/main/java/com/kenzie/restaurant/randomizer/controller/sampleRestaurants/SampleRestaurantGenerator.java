package com.kenzie.restaurant.randomizer.controller.sampleRestaurants;

import com.kenzie.restaurant.randomizer.controller.RestaurantController;
import com.kenzie.restaurant.randomizer.service.model.Restaurant;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SampleRestaurantGenerator {

    public  List<Restaurant> readData() throws IOException {

        List<Restaurant> restaurantList = new ArrayList<>(); // Will hold the restaurants, so they don't go poof
        List<String[]> stringList = new ArrayList<>();


        try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\Owner\\ata-lbc-project-Kane-Ryan-Jonathan\\Application\\src\\main\\java\\com\\kenzie\\restaurant\\randomizer\\controller\\sampleRestaurants\\Restaurant.csv"))) {

            // CSV file delimiter
            String DELIMITER = ",";

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {

                // convert line into columns
                String[] columns = line.split(DELIMITER);
                for (int i = 0; i < columns.length; i++) {
                    columns[i] = columns[i].replaceAll("^\"|\"$", "");

                }

                stringList.add(columns);

            }
        } catch (IOException ex) {
            ex.printStackTrace();

        }
        // Have to iterate through the String array for each restaurant object
        for (String[] detailsRestaurant : stringList) {
            Restaurant restaurant = new Restaurant();
            UUID uuid = UUID.randomUUID();
            restaurant.setRestaurantId(uuid);
            restaurant.setAveragePrice(Double.valueOf((detailsRestaurant[1])));
            restaurant.setAverageRating(Double.valueOf(detailsRestaurant[2]));
            restaurant.setCategory(detailsRestaurant[3]);
            restaurant.setRestaurantName(detailsRestaurant[4]);

            restaurantList.add(restaurant);

        }
        System.out.println(restaurantList);
        return restaurantList;
    }


}
