package com.zeilvoortgang.education.entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table( "lessen")
public class Les {

    @Id
    @Expose
    @Schema(title = "Les identifier, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID lesId;

    @Expose
    @Schema(title = "Les sequence identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID lesSequenceId;

    @Expose
    @Schema(title = "Les Timestamp, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private Timestamp lesDateTime;

    @Expose
    @Schema(title = "Les title, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private String title;

    @Expose
    @Schema(title = "Les description, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Les status, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private String status;

    @Expose
    @Schema(title = "Les afgesloten, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private Timestamp afgesloten;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getLesId() {
        return lesId;
    }

    public void setLesId(UUID lesId) {
        this.lesId = lesId;
    }

    public UUID getLesSequenceId() {
        return lesSequenceId;
    }

    public void setLesSequenceId(UUID lesSequenceId) {
        this.lesSequenceId = lesSequenceId;
    }

    public Timestamp getLesDateTime() {
        return lesDateTime;
    }

    public void setLesDateTime(Timestamp lesDateTime) {
        this.lesDateTime = lesDateTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getAfgesloten() {
        return afgesloten;
    }

    public void setAfgesloten(Timestamp afgesloten) {
        this.afgesloten = afgesloten;
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