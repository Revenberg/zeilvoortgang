package com.zeilvoortgang.education.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("leerlijnLes")
public class LeerlijnLes {

    @Id
    @Expose
    @Schema(title = "LeerlijnLes identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID leerlijnLesId;

    @Expose
    @Schema(title = "Leerlijn identifier, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID leerlijnId;

    @Expose
    @Schema(title = "Leerlijn level identifier, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID levelId;

    @Expose
    @Schema(title = "Les volgorde, C:3", required = true, accessMode = AccessMode.READ_WRITE)
    private int lesOrder;

    @Expose
    @Schema(title = "Title of the leerlijnLes, C:4", required = true, accessMode = AccessMode.READ_WRITE)
    private String title;

    @Expose
    @Schema(title = "Description of the leerlijnLes, C:5", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Last update timestamp, C:6", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:7", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getLeerlijnLesId() {
        return leerlijnLesId;
    }

    public void setLeerlijnLesId(UUID leerlijnLesId) {
        this.leerlijnLesId = leerlijnLesId;
    }

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

    public int getLesOrder() {
        return lesOrder;
    }

    public void setLesOrder(int lesOrder) {
        this.lesOrder = lesOrder;
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