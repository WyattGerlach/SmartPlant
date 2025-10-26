package com.smartplant.backend.service;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;
import com.smartplant.backend.model.PlantDoc;
import com.smartplant.backend.model.WateringEventDoc;
import com.smartplant.backend.repository.PlantRepository;
import com.smartplant.backend.repository.WateringEventRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("mongo")
public class MongoPlantService implements PlantServiceApi {
    private final PlantRepository plantRepository;
    private final WateringEventRepository wateringRepository;

    public MongoPlantService(PlantRepository plantRepository, WateringEventRepository wateringRepository) {
        this.plantRepository = plantRepository;
        this.wateringRepository = wateringRepository;
    }

    @Override
    public Collection<PlantDto> listPlants() {
        return plantRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public PlantDto createPlant(PlantDto dto) {
        PlantDoc doc = new PlantDoc();
        doc.setName(dto.getName());
        doc.setSpecies(dto.getSpecies());
        doc.setPotVolumeLiters(dto.getPotVolumeLiters());
        doc.setSoilType(dto.getSoilType());
        doc.setCreatedAt(Instant.now());
        doc.setTotalWateredMl(0L);
        PlantDoc saved = plantRepository.save(doc);
        return toDto(saved);
    }

    @Override
    public Optional<PlantDto> getPlant(String id) {
        return plantRepository.findById(id).map(this::toDto);
    }

    @Override
    public Optional<PlantDto> recordWatering(String id, WateringDto w) {
        return plantRepository.findById(id).map(p -> {
            if (w.getTimestamp() == null) w.setTimestamp(Instant.now());
            p.setLastWateredAt(w.getTimestamp());
            p.setTotalWateredMl(p.getTotalWateredMl() + w.getVolumeMl());
            plantRepository.save(p);

            WateringEventDoc e = new WateringEventDoc();
            e.setPlantId(p.getId());
            e.setSource(w.getSource());
            e.setVolumeMl(w.getVolumeMl());
            e.setTs(w.getTimestamp());
            wateringRepository.save(e);
            return toDto(p);
        });
    }

    @Override
    public List<WateringDto> listWaterings(String id) {
        return wateringRepository.findByPlantIdOrderByTsDesc(id, PageRequest.of(0, 500)).stream()
                .map(e -> {
                    WateringDto w = new WateringDto();
                    w.setSource(e.getSource());
                    w.setVolumeMl(e.getVolumeMl());
                    w.setTimestamp(e.getTs());
                    return w;
                })
                .collect(Collectors.toList());
    }

    private PlantDto toDto(PlantDoc d) {
        PlantDto dto = new PlantDto();
        dto.setId(d.getId());
        dto.setName(d.getName());
        dto.setSpecies(d.getSpecies());
        dto.setPotVolumeLiters(d.getPotVolumeLiters());
        dto.setSoilType(d.getSoilType());
        dto.setCreatedAt(d.getCreatedAt());
        dto.setLastWateredAt(d.getLastWateredAt());
        dto.setTotalWateredMl(d.getTotalWateredMl());
        return dto;
    }
}

