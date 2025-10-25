package com.smartplant.backend.dto;

import java.time.Instant;

public class PlantDto {
    private Long id;
    private String name;
    private String species;
    private Double potVolumeLiters;
    private String soilType;
    private Instant createdAt;
    private Instant lastWateredAt;
    private long totalWateredMl = 0L;

    public PlantDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public Double getPotVolumeLiters() { return potVolumeLiters; }
    public void setPotVolumeLiters(Double potVolumeLiters) { this.potVolumeLiters = potVolumeLiters; }

    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getLastWateredAt() { return lastWateredAt; }
    public void setLastWateredAt(Instant lastWateredAt) { this.lastWateredAt = lastWateredAt; }

    public long getTotalWateredMl() { return totalWateredMl; }
    public void setTotalWateredMl(long totalWateredMl) { this.totalWateredMl = totalWateredMl; }
}
