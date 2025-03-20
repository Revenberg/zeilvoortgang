package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("leerlijn")
public class Leerlijn {

    @Id
    @Expose
    @Schema(title = "Leerlijn identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID leerlijnId;

    @Expose
    @Schema(title = "Leerlijn level identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID levelId;

    @Expose
    @Schema(title = "Title of the leerlijn, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private String title;

    @Expose
    @Schema(title = "Description of the leerlijn, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Last update timestamp, C:4", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:5", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getLeerlijnId() {
        return leerlijnId;
    }

    public void setLeerlijnId(UUID leerlijnId) {
        this.leerlijnId = leerlijnId;
    }

    public UUID getLevelId() {
        return levelId;
    }

    public void setLevelId(UUID levelId) {
        this.levelId = levelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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