package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("voortgangStatussen")
public class VoortgangStatussen {

    @Id
    @Expose
    @Schema(title = "VoortgangStatussen identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID voortgangStatussenId;

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

    public UUID getVoortgangStatussenId() {
        return voortgangStatussenId;
    }

    public void setVoortgangStatussenId(UUID voortgangStatussenId) {
        this.voortgangStatussenId = voortgangStatussenId;
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