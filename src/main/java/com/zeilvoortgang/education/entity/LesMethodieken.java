package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("lesmethodieken")
public class LesMethodieken {

    @Id
    @Expose
    @Schema(title = "LesMethodieken identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID lesMethodiekenId;

    @Expose
    @Schema(title = "Les identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID lesId;

    @Expose
    @Schema(title = "Methodiek identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID methodiekId;

    @Expose
    @Schema(title = "Doel identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID doelId;

    @Expose
    @Schema(title = "Description, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getLesMethodiekenId() {
        return lesMethodiekenId;
    }

    public void setLesMethodiekenId(UUID lesMethodiekenId) {
        this.lesMethodiekenId = lesMethodiekenId;
    }

    public UUID getLesId() {
        return lesId;
    }

    public void setLesId(UUID lesId) {
        this.lesId = lesId;
    }

    public UUID getMethodiekId() {
        return methodiekId;
    }

    public void setMethodiekId(UUID methodiekId) {
        this.methodiekId = methodiekId;
    }

    public UUID getDoelId() {
        return doelId;
    }

    public void setDoelId(UUID doelId) {
        this.doelId = doelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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