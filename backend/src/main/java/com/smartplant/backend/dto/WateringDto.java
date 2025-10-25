package com.smartplant.backend.dto;

import java.time.Instant;

public class WateringDto {
    private long volumeMl;
    private String source; // manual or auto
    private Instant timestamp;

    public WateringDto() {}

    public long getVolumeMl() { return volumeMl; }
    public void setVolumeMl(long volumeMl) { this.volumeMl = volumeMl; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
