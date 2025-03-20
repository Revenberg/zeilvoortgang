package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.TeamPermission;
import com.zeilvoortgang.usermanagement.repository.TeamPermissionRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/team-permissions")
public class TeamPermissionController {

    @Autowired
    private TeamPermissionRepository teamPermissionRepository;

    @ApiOperation(value = "Get a team permission by teamId and groupPermissionId")
    @GetMapping("/{teamId}/{groupPermissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team permission retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Team permission not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<TeamPermission> getTeamPermissionById(@PathVariable String teamId, @PathVariable String groupPermissionId) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UUID groupPermissionUUID;
        try {
            groupPermissionUUID = UUID.fromString(groupPermissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return teamPermissionRepository
                .findTeamPermissionById(teamUUID, groupPermissionUUID)
                .map(teamPermission -> new ResponseEntity<>(teamPermission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new team permission")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team permission created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate team permission"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<TeamPermission> createTeamPermission(@RequestBody TeamPermission teamPermission) {
        teamPermission.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(teamPermissionRepository.saveTeamPermission(teamPermission), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a team permission by teamId and groupPermissionId")
    @DeleteMapping("/{teamId}/{groupPermissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team permission deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Team permission not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteTeamPermission(@PathVariable String teamId, @PathVariable String groupPermissionId) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UUID groupPermissionUUID;
        try {
            groupPermissionUUID = UUID.fromString(groupPermissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            teamPermissionRepository.deleteByTeamPermissionId(teamUUID, groupPermissionUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}