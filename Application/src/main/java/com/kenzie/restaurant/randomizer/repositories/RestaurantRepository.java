package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.RestaurantRecord;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<RestaurantRecord, String> {

}
