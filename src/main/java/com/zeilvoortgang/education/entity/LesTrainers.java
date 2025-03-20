package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("lestrainers")
public class LesTrainers {

    @Id
    @Expose
    @Schema(title = "LesTrainers identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID lesTrainersId;

    @Expose
    @Schema(title = "Les identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID lesId;

    @Expose
    @Schema(title = "Trainer identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID trainerId;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getLesTrainersId() {
        return lesTrainersId;
    }

    public void setLesTrainersId(UUID lesTrainersId) {
        this.lesTrainersId = lesTrainersId;
    }

    public UUID getLesId() {
        return lesId;
    }

    public void setLesId(UUID lesId) {
        this.lesId = lesId;
    }

    public UUID getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(UUID trainerId) {
        this.trainerId = trainerId;
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