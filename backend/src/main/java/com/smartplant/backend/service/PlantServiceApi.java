package com.smartplant.backend.service;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlantServiceApi {
    Collection<PlantDto> listPlants();
    PlantDto createPlant(PlantDto dto);
    Optional<PlantDto> getPlant(String id);
    Optional<PlantDto> recordWatering(String id, WateringDto w);
    List<WateringDto> listWaterings(String id);
}

