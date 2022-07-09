package com.kenzie.restaurant.randomizer.controller.sampleRestaurants;

import com.kenzie.restaurant.randomizer.service.model.Restaurant;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SampleRestaurantGenerator {

    public static List<Restaurant> readData() throws IOException {

       // TODO Make a restaurant object list.
        // return list, so it doesn't go poof
        try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\Owner\\ata-lbc-project-Kane-Ryan-Jonathan\\Application\\src\\main\\java\\com\\kenzie\\restaurant\\randomizer\\controller\\sampleRestaurants\\Restaurant.csv"))) {

            // CSV file delimiter
            String DELIMITER = ",";

            // read the file line by line
            String line;
            while ((line = br.readLine()) != null) {

                // convert line into columns
                String[] columns = line.split(DELIMITER);

                // print all columns
                System.out.println("Restaurant[" + String.join(", ", columns) + "]");
            }

        } catch (IOException ex) {
            ex.printStackTrace();

        }
        return null;
    }
    public static void main (String[]args) throws IOException {
readData();
        }
    }
