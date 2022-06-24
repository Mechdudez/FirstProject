package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
@EnableScan
public interface RestaurantRepository extends CrudRepository<RestaurantRecord, String> {
    List<RestaurantRecord> findMyRestaurant(String restaurant);
}
