package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("leerlijnLesDetails")
public class LeerlijnLesDetails {

    @Id
    @Expose
    @Schema(title = "LeerlijnLesDetails identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID leerlijnLesDetailsId;

    @Expose
    @Schema(title = "Leerlijn les Id identifier, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID leerlijnLesId;

    @Expose
    @Schema(title = "methodiek identifier, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID methodiekId;

    @Expose
    @Schema(title = "progressStatus identifier, C:4", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID progressStatusId;

    @Expose
    @Schema(title = "Last update timestamp, C:7", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:8", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getLeerlijnLesDetailsId() {
        return leerlijnLesDetailsId;
    }

    public void setLeerlijnLesDetailsId(UUID leerlijnLesDetailsId) {
        this.leerlijnLesDetailsId = leerlijnLesDetailsId;
    }

    public UUID getLeerlijnLesId() {
        return leerlijnLesId;
    }

    public void setLeerlijnLesId(UUID leerlijnLesId) {
        this.leerlijnLesId = leerlijnLesId;
    }

    public UUID getMethodiekId() {
        return methodiekId;
    }

    public void setMethodiekId(UUID methodiekId) {
        this.methodiekId = methodiekId;
    }

    public UUID getProgressStatusId() {
        return progressStatusId;
    }

    public void setProgressStatusId(UUID progressStatusId) {
        this.progressStatusId = progressStatusId;
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