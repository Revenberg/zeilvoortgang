package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("authorizationGroups")
public class AuthorizationGroups {

    @Id
    @Expose
    @Schema(title = "Authorization Group Unique Identifier ID", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID authorizationGroupsId;

    @Expose
    @Schema(title = "Authorization ID associated with the group", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID authorizationId;

    @Expose
    @Schema(title = "Authorization Role ID associated with the group", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID authorizationRoleId;

    @Expose
    @Schema(title = "Last update timestamp", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getAuthorizationGroupsId() {
        return authorizationGroupsId;
    }

    public void setAuthorizationGroupsId(UUID authorizationGroupsId) {
        this.authorizationGroupsId = authorizationGroupsId;
    }

    public UUID getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(UUID authorizationId) {
        this.authorizationId = authorizationId;
    }

    public UUID getAuthorizationRoleId() {
        return authorizationRoleId;
    }

    public void setAuthorizationRoleId(UUID authorizationRoleId) {
        this.authorizationRoleId = authorizationRoleId;
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