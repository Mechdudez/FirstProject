package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@EnableScan
public interface RestaurantRepository extends CrudRepository<RestaurantRecord, UUID> {
//    List<RestaurantRecord> findMyRestaurant(String restaurant);
}
