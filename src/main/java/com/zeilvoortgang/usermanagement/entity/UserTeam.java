package com.zeilvoortgang.usermanagement.entity;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.google.gson.annotations.Expose;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.media.Schema;

@Table("User_Team")
public class UserTeam {
    @Id
    @Expose
    @Schema(title = "User Team ID", accessMode = AccessMode.READ_ONLY)
    private UUID userTeamId;

    @Expose
    @Schema(title = "User ID", accessMode = AccessMode.READ_WRITE)
    private UUID userId;

    @Expose
    @Schema(title = "Team ID", accessMode = AccessMode.READ_WRITE)
    private UUID teamId;

    @Expose
    @Schema(title = "Last update timestamp, C:1", required = true, accessMode = AccessMode.READ_ONLY)
    private Timestamp lastUpdateTMS;

    @Expose
    @Schema(title = "Last update identifier, C:1", required = true,	accessMode = AccessMode.READ_WRITE)
    private String lastUpdateIdentifier;
    
        // Getters and Setters
        public UUID getUserTeamId() {
            return userTeamId;
        }

        public void setUserTeamId(UUID userTeamId) {
            this.userTeamId = userTeamId;
        }

        public UUID getUserId() {
            return userId;
        }
    
        public void setUserId(UUID userId) {
            this.userId = userId;
        }
    
        public UUID getTeamId() {
            return teamId;
        }
    
        public void setTeamId(UUID teamId) {
            this.teamId = teamId;
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