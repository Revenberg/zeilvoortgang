package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.Permission;
import com.zeilvoortgang.usermanagement.repository.PermissionRepository;

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
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @ApiOperation(value = "Get a permission by permissionId")
    @GetMapping("/{permissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Permission retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Permission not found"),
    })
    public ResponseEntity<Permission> getPermissionById(@PathVariable String permissionId) {
        UUID permissionUUID;
        try {
            permissionUUID = UUID.fromString(permissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return permissionRepository.findPermissionById(permissionUUID)
                .map(permission -> new ResponseEntity<>(permission, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Create a new permission")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Permission created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate permission"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        permission.setPermissionId(UUID.randomUUID());
        permission.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(permissionRepository.savePermission(permission), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a permission by permissionId")
    @PutMapping("/{permissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Permission updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Permission not found"),
        @ApiResponse(code = 409, message = "Duplicate permission"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Permission> updatePermission(@PathVariable String permissionId, @RequestBody Permission permission) {
        UUID permissionUUID;
        try {
            permissionUUID = UUID.fromString(permissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        permission.setPermissionId(permissionUUID);
        permission.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(permissionRepository.updatePermission(permission), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a permission by permissionId")
    @DeleteMapping("/{permissionId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Permission deleted successfully"),
        @ApiResponse(code = 404, message = "Permission not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deletePermission(@PathVariable String permissionId) {
        UUID permissionUUID;
        try {
            permissionUUID = UUID.fromString(permissionId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            permissionRepository.deleteByPermissionId(permissionUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}