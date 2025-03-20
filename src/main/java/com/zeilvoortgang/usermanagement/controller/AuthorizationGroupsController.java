package com.zeilvoortgang.usermanagement.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zeilvoortgang.usermanagement.entity.AuthorizationGroups;
import com.zeilvoortgang.usermanagement.repository.AuthorizationGroupsRepository;

@RestController
@RequestMapping("/api/authorizationGroups")
public class AuthorizationGroupsController {

    @Autowired
    private AuthorizationGroupsRepository authorizationGroupsRepository;

    @ApiOperation(value = "Get an authorization group by groupId")
    @GetMapping("/{groupId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization group retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization group not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationGroups> getAuthorizationGroupsById(@PathVariable String groupId) {
        UUID groupUUID;
        try {
            groupUUID = UUID.fromString(groupId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return authorizationGroupsRepository.findAuthorizationGroupById(groupUUID)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new authorization group")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization group created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate authorization group"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationGroups> createAuthorizationGroups(@RequestBody AuthorizationGroups group) {
        group.setAuthorizationGroupsId(UUID.randomUUID());
        group.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationGroupsRepository.saveAuthorizationGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update an authorization group by groupId")
    @PutMapping("/{groupId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization group updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization group not found"),
        @ApiResponse(code = 409, message = "Duplicate authorization group"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationGroups> updateAuthorizationGroups(@PathVariable String groupId, @RequestBody AuthorizationGroups group) {
        UUID groupUUID;
        try {
            groupUUID = UUID.fromString(groupId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        group.setAuthorizationGroupsId(groupUUID);
        group.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationGroupsRepository.updateAuthorizationGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete an authorization group by groupId")
    @DeleteMapping("/{groupId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization group deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization group not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteAuthorizationGroups(@PathVariable String groupId) {
        UUID groupUUID;
        try {
            groupUUID = UUID.fromString(groupId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            authorizationGroupsRepository.deleteAuthorizationGroupById(groupUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}