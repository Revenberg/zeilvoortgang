package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("Group")
public class Group {
    @Id
    @Expose
    @Schema(title = "Group Unique permission Group indentifier ID, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private UUID groupId;

    @Expose
    @Schema(title = "Permission group name, C:2", required = true, accessMode = AccessMode.READ_WRITE)
    private String name;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;

    // Getters and Setters

    public UUID getGroupId() {
        return groupId;
    }

    public void setGroupId(UUID groupId) {
        this.groupId = groupId;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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