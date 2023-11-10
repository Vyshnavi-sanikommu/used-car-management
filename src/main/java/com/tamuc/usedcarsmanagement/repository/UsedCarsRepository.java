package com.tamuc.usedcarsmanagement.repository;

import com.tamuc.usedcarsmanagement.usedcarsPojo.UsedCarsPojo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsedCarsRepository extends MongoRepository<UsedCarsPojo, String> {
    Optional<List<UsedCarsPojo>> findByCarModel(String carModel);
    Optional<List<UsedCarsPojo>> findByCarMake(String carMake);
}
