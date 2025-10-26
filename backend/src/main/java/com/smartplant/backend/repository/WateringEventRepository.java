package com.smartplant.backend.repository;

import com.smartplant.backend.model.WateringEventDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WateringEventRepository extends MongoRepository<WateringEventDoc, String> {
    List<WateringEventDoc> findByPlantIdOrderByTsDesc(String plantId, Pageable pageable);
}

