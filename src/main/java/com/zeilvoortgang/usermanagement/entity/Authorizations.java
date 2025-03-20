package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("authorizations")
public class Authorizations {

    @Id
    @Expose
    @Schema(title = "Authorization Unique Identifier ID", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID authorizationId;

    @Expose
    @Schema(title = "Description of the authorization", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Page associated with the authorization", required = true, accessMode = AccessMode.READ_WRITE)
    private String page;

    @Expose
    @Schema(title = "RACI (Responsible, Accountable, Consulted, Informed)", required = true, accessMode = AccessMode.READ_WRITE)
    private String raci;

    @Expose
    @Schema(title = "Last update timestamp", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(UUID authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRaci() {
        return raci;
    }

    public void setRaci(String raci) {
        this.raci = raci;
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