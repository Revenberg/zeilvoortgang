package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("authorizationRoles")
public class AuthorizationRoles {

    @Id
    @Expose
    @Schema(title = "Authorization Role Unique Identifier ID", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID authorizationRoleId;

    @Expose
    @Schema(title = "Role name", required = true, accessMode = AccessMode.READ_WRITE)
    private String roleName;

    @Expose
    @Schema(title = "Last update timestamp", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getAuthorizationRoleId() {
        return authorizationRoleId;
    }

    public void setAuthorizationRoleId(UUID authorizationRoleId) {
        this.authorizationRoleId = authorizationRoleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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