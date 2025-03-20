package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("lesStatussen")
public class LesStatussen {

    @Id
    @Expose
    @Schema(title = "LesStatussen identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID lesStatusId;

    @Expose
    @Schema(title = "Les identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID lesId;

    @Expose
    @Schema(title = "Methodiek identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID methodiekId;

    @Expose
    @Schema(title = "User identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID userId;

    @Expose
    @Schema(title = "Progress identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID progressId;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getLesStatusId() {
        return lesStatusId;
    }

    public void setLesStatusId(UUID lesStatusId) {
        this.lesStatusId = lesStatusId;
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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getProgressId() {
        return progressId;
    }

    public void setProgressId(UUID progressId) {
        this.progressId = progressId;
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