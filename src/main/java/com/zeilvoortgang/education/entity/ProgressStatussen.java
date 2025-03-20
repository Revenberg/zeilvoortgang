package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("ProgressStatussen")
public class ProgressStatussen {
    @Id
    @Expose
    @Schema(title = "ProgressStatussen Unique identifier ID, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID progressStatusId;

    @Expose
    @Schema(title = "Description, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Long Description, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private String longDescription;

    @Expose
    @Schema(title = "Last update timestamp, C:4", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:5", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getProgressStatusId() {
        return progressStatusId;
    }

    public void setProgressStatusId(UUID progressStatusId) {
        this.progressStatusId = progressStatusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}