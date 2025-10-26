package com.smartplant.backend.controller;

import com.smartplant.backend.dto.PlantDto;
import com.smartplant.backend.dto.WateringDto;
import com.smartplant.backend.service.PlantServiceApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private final PlantServiceApi plantService;

    public PlantController(PlantServiceApi plantService) {
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
    public ResponseEntity<PlantDto> get(@PathVariable String id) {
        return plantService.getPlant(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/water")
    public ResponseEntity<PlantDto> water(@PathVariable String id, @RequestBody WateringDto w) {
        return plantService.recordWatering(id, w).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
