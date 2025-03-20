package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;
import com.zeilvoortgang.usermanagement.repository.AuthorizationRolesRepository;

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
@RequestMapping("/api/authorization-roles")
public class AuthorizationRolesController {

    @Autowired
    private AuthorizationRolesRepository authorizationRolesRepository;

    @ApiOperation(value = "Get an authorization role by roleId")
    @GetMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization role retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization role not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationRoles> getAuthorizationRolesById(@PathVariable String roleId) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return authorizationRolesRepository.findAuthorizationRoleById(roleUUID)
                .map(role -> new ResponseEntity<>(role, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new authorization role")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization role created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate role name"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationRoles> createAuthorizationRoles(@RequestBody AuthorizationRoles role) {
        role.setAuthorizationRoleId(UUID.randomUUID());
        role.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationRolesRepository.saveAuthorizationRole(role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update an authorization role by roleId")
    @PutMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization role updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization role not found"),
        @ApiResponse(code = 409, message = "Duplicate role name"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<AuthorizationRoles> updateAuthorizationRoles(@PathVariable String roleId, @RequestBody AuthorizationRoles role) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        role.setAuthorizationRoleId(roleUUID);
        role.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationRolesRepository.updateAuthorizationRole(role), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete an authorization role by roleId")
    @DeleteMapping("/{roleId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorization role deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorization role not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteAuthorizationRoles(@PathVariable String roleId) {
        UUID roleUUID;
        try {
            roleUUID = UUID.fromString(roleId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            authorizationRolesRepository.deleteAuthorizationRoleById(roleUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}