package com.smartplant.backend.service;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;
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
public class PlantService {
    private final Map<Long, PlantDto> storage = new ConcurrentHashMap<>();
    private final Map<Long, List<WateringDto>> waterings = new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong(1);

    public Collection<PlantDto> listPlants() {
        return storage.values();
    }

    public PlantDto createPlant(PlantDto dto) {
        Long id = idGen.getAndIncrement();
        dto.setId(id);
        dto.setCreatedAt(Instant.now());
        storage.put(id, dto);
        waterings.put(id, new ArrayList<>());
        return dto;
    }

    public Optional<PlantDto> getPlant(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Optional<PlantDto> recordWatering(Long id, WateringDto w) {
        PlantDto p = storage.get(id);
        if (p == null) return Optional.empty();
        if (w.getTimestamp() == null) w.setTimestamp(Instant.now());
        p.setLastWateredAt(w.getTimestamp());
        p.setTotalWateredMl(p.getTotalWateredMl() + w.getVolumeMl());
        waterings.get(id).add(w);
        return Optional.of(p);
    }

    public List<WateringDto> listWaterings(Long id) {
        return waterings.getOrDefault(id, new ArrayList<>());
    }
}
