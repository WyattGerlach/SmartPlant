package com.smartplant.backend.controller;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;
import com.smartplant.backend.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping
    public Collection<PlantDto> list() {
        return plantService.listPlants();
    }

    @PostMapping
    public ResponseEntity<PlantDto> create(@RequestBody PlantDto dto) {
        PlantDto created = plantService.createPlant(dto);
        return ResponseEntity.created(URI.create("/api/plants/" + created.getId())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlantDto> get(@PathVariable Long id) {
        return plantService.getPlant(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/water")
    public ResponseEntity<PlantDto> water(@PathVariable Long id, @RequestBody WateringDto w) {
        return plantService.recordWatering(id, w).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
