package com.smartplant.backend.service;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Profile("!mongo")
public class InMemoryPlantService implements PlantServiceApi {
    private final Map<String, PlantDto> storage = new ConcurrentHashMap<>();
    private final Map<String, List<WateringDto>> waterings = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    @Override
    public Collection<PlantDto> listPlants() {
        return storage.values();
    }

    @Override
    public PlantDto createPlant(PlantDto dto) {
        String id = String.valueOf(idGen.getAndIncrement());
        dto.setId(id);
        dto.setCreatedAt(Instant.now());
        storage.put(id, dto);
        waterings.put(id, new ArrayList<>());
        return dto;
    }

    @Override
    public Optional<PlantDto> getPlant(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<PlantDto> recordWatering(String id, WateringDto w) {
        PlantDto p = storage.get(id);
        if (p == null) return Optional.empty();
        if (w.getTimestamp() == null) w.setTimestamp(Instant.now());
        p.setLastWateredAt(w.getTimestamp());
        p.setTotalWateredMl(p.getTotalWateredMl() + w.getVolumeMl());
        waterings.get(id).add(w);
        return Optional.of(p);
    }

    @Override
    public List<WateringDto> listWaterings(String id) {
        return waterings.getOrDefault(id, new ArrayList<>());
    }
}
