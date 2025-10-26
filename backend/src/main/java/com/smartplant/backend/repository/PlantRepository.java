package com.smartplant.backend.repository;

import com.smartplant.backend.model.PlantDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlantRepository extends MongoRepository<PlantDoc, String> {
}

