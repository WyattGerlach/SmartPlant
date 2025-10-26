package com.smartplant.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "watering_events")
public class WateringEventDoc {
    @Id
    private String id;
    private String plantId;
    private long volumeMl;
    private String source; // manual or auto

    @Indexed(name = "watering_ttl_ts_idx", expireAfterSeconds = 5184000)
    private Instant ts; // event timestamp; TTL uses this field

    public WateringEventDoc() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPlantId() { return plantId; }
    public void setPlantId(String plantId) { this.plantId = plantId; }

    public long getVolumeMl() { return volumeMl; }
    public void setVolumeMl(long volumeMl) { this.volumeMl = volumeMl; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Instant getTs() { return ts; }
    public void setTs(Instant ts) { this.ts = ts; }
}
