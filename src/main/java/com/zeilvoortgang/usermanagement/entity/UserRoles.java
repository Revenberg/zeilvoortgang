package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("userRoles")
public class UserRoles {

    @Id
    @Expose
    @Schema(title = "User Role Unique Identifier ID", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID userRolesId;

    @Expose
    @Schema(title = "User ID associated with the role", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID userId;

    @Expose
    @Schema(title = "Authorization Role ID associated with the user", required = true, accessMode = AccessMode.READ_WRITE)
    private UUID authorizationRoleId;

    @Expose
    @Schema(title = "Last update timestamp", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getUserRolesId() {
        return userRolesId;
    }

    public void setUserRolesId(UUID userRolesId) {
        this.userRolesId = userRolesId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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