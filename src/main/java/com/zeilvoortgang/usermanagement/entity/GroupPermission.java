package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("Group_Permission")
public class GroupPermission {
    @Id
    @Expose
    @Schema(title = "Group Permission ID", accessMode = AccessMode.READ_ONLY)
    private UUID groupPermissionId;

    @Expose
    @Schema(title = "Description", required = true, accessMode = AccessMode.READ_WRITE)
    private String description;

    @Expose
    @Schema(title = "Group ID", accessMode = AccessMode.READ_WRITE)
    private UUID groupId;

    @Expose
    @Schema(title = "Permission ID", accessMode = AccessMode.READ_WRITE)
    private UUID permissionId;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true, accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters
    public UUID getGroupPermissionId() {
        return groupPermissionId;
    }

    public void setGroupPermissionId(UUID groupPermissionId) {
        this.groupPermissionId = groupPermissionId;
    }

    public UUID getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(UUID permissionId) {
        this.permissionId = permissionId;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }
}