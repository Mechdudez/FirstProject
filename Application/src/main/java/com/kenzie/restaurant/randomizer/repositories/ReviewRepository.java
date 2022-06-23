package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<ReviewRecord, String> {

}
