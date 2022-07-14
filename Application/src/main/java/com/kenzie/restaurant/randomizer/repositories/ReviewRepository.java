package com.kenzie.restaurant.randomizer.repositories;

import com.kenzie.restaurant.randomizer.repositories.model.ReviewRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@EnableScan
@Repository
public interface ReviewRepository extends CrudRepository<ReviewRecord, UUID> {

}
