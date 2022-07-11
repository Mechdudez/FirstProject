package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface ReviewRepository extends CrudRepository<ReviewRecord, String> {
    //TODO: below method should be called by service when getting random restaurant with filter
//    List<ReviewRecord> sortMyReview(Double price, String review);
}
