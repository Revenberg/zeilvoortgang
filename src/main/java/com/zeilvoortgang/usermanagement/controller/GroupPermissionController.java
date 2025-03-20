package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.GroupPermission;
import com.zeilvoortgang.usermanagement.repository.GroupPermissionRepository;

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
@RequestMapping("/api/group-permissions")
public class GroupPermissionController {

    @Autowired
    private GroupPermissionRepository groupPermissionRepository;

    @ApiOperation(value = "Get a group permission by groupPermissionId")
    @GetMapping("/{groupPermissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group permission retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Group permission not found"),
    })
    public ResponseEntity<GroupPermission> getGroupPermissionById(@PathVariable String groupPermissionId) {
        UUID groupPermissionUUID;
        try {
            groupPermissionUUID = UUID.fromString(groupPermissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return groupPermissionRepository.findGroupPermissionById(groupPermissionUUID)
                .map(groupPermission -> new ResponseEntity<>(groupPermission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new group permission")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group permission created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate group permission"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<GroupPermission> createGroupPermission(@RequestBody GroupPermission groupPermission) {
        groupPermission.setGroupPermissionId(UUID.randomUUID());
        groupPermission.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(groupPermissionRepository.saveGroupPermission(groupPermission), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a group permission by groupPermissionId")
    @PutMapping("/{groupPermissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group permission updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Group permission not found"),
        @ApiResponse(code = 409, message = "Duplicate group permission"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<GroupPermission> updateGroupPermission(@PathVariable String groupPermissionId, @RequestBody GroupPermission groupPermission) {
        UUID groupPermissionUUID;
        try {
            groupPermissionUUID = UUID.fromString(groupPermissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        groupPermission.setGroupPermissionId(groupPermissionUUID);
        groupPermission.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(groupPermissionRepository.updateGroupPermission(groupPermission), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a group permission by groupPermissionId")
    @DeleteMapping("/{groupPermissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group permission deleted successfully"),
        @ApiResponse(code = 404, message = "Group permission not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteGroupPermission(@PathVariable String groupPermissionId) {
        UUID groupPermissionUUID;
        try {
            groupPermissionUUID = UUID.fromString(groupPermissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            groupPermissionRepository.deleteByGroupPermissionId(groupPermissionUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}