package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("Methodieken")
public class Methodieken {
    @Id
    @Expose
    @Schema(title = "Methodieken Unique identifier ID, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID methodiekId;

    @Expose
    @Schema(title = "Level ID, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID levelId;

    @Expose
    @Schema(title = "Soort, C:5", required = true, accessMode = AccessMode.READ_WRITE)
    private String soort;
    
    @Expose
    @Schema(title = "methodiek, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private String methodiek;

    @Expose
    @Schema(title = "Description, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Last update timestamp, C:6", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:7", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getMethodiekId() {
        return methodiekId;
    }

    public void setMethodiekId(UUID methodiekId) {
        this.methodiekId = methodiekId;
    }

    public UUID getLevelId() {
        return levelId;
    }

    public void setLevelId(UUID levelId) {
        this.levelId = levelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMethodiek() {
        return methodiek;
    }

    public void setMethodiek(String methodiek) {
        this.methodiek = methodiek;
    }
    
    public String getSoort() {
        return soort;
    }

    public void setSoort(String soort) {
        this.soort = soort;
    }

    public Timestamp getLastUpdateTMS() {
        return lastUpdateTMS;
    }

    public void setLastUpdateTMS(Timestamp lastUpdateTMS) {
        this.lastUpdateTMS = lastUpdateTMS;
    }

    public String getLastUpdateIdentifier() {
        return lastUpdateIdentifier;
    }

    public void setLastUpdateIdentifier(String lastUpdateIdentifier) {
        this.lastUpdateIdentifier = lastUpdateIdentifier;
    }
}