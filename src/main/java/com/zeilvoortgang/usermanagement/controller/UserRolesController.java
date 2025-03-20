package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.UserRoles;
import com.zeilvoortgang.usermanagement.repository.UserRolesRepository;

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
@RequestMapping("/api/user-roles")
public class UserRolesController {

    @Autowired
    private UserRolesRepository userRolesRepository;

    @ApiOperation(value = "Get a user role by roleId")
    @GetMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User role retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User role not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserRoles> getUserRolesById(@PathVariable String roleId) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return userRolesRepository.findUserRoleById(roleUUID)
                .map(role -> new ResponseEntity<>(role, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new user role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User role created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate user role"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserRoles> createUserRoles(@RequestBody UserRoles role) {
        role.setUserRolesId(UUID.randomUUID());
        role.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(userRolesRepository.saveUserRole(role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a user role by roleId")
    @PutMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User role updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User role not found"),
        @ApiResponse(code = 409, message = "Duplicate user role"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserRoles> updateUserRoles(@PathVariable String roleId, @RequestBody UserRoles role) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        role.setUserRolesId(roleUUID);
        role.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(userRolesRepository.updateUserRole(role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a user role by roleId")
    @DeleteMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User role deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User role not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteUserRoles(@PathVariable String roleId) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userRolesRepository.deleteUserRoleById(roleUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}