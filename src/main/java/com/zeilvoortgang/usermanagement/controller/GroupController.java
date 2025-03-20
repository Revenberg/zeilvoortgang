package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.Group;
import com.zeilvoortgang.usermanagement.repository.GroupRepository;

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
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @ApiOperation(value = "Get a group by groupId")
    @GetMapping("/{groupId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Group not found"),
    })
    public ResponseEntity<Group> getGroupById(@PathVariable String groupId) {
        UUID groupUUID;
        try {
            groupUUID = UUID.fromString(groupId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return groupRepository.findGroupById(groupUUID)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new group")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate group"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        group.setGroupId(UUID.randomUUID());
        group.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(groupRepository.saveGroup(group), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a group by groupId")
    @DeleteMapping("/{groupId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Group deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Group not found"),
    })
    public ResponseEntity<Void> deleteGroup(@PathVariable String groupId) {
        UUID groupUUID;
        try {
            groupUUID = UUID.fromString(groupId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!groupRepository.findGroupById(groupUUID).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        groupRepository.deleteByGroupId(groupUUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}