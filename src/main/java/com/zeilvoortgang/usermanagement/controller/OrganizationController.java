package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.Organization;
import com.zeilvoortgang.usermanagement.repository.OrganizationRepository;

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
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @ApiOperation(value = "Get an organization by organizationId")
    @GetMapping("/{organizationId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Organization retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Organization not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Organization> getOrganizationById(@PathVariable String organizationId) {
        UUID organizationUUID;
        try {
            organizationUUID = UUID.fromString(organizationId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return organizationRepository.findOrganizationById(organizationUUID)
                .map(organization -> new ResponseEntity<>(organization, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new organization")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Organization created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate name or email"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Organization> createOrganization(@RequestBody Organization organization) {
        organization.setOrganizationId(UUID.randomUUID());
        organization.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(organizationRepository.saveOrganization(organization), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update an organization by organizationId")
    @PutMapping("/{organizationId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Organization updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Organization not found"),
        @ApiResponse(code = 409, message = "Duplicate name or email"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Organization> updateOrganization(@PathVariable String organizationId, @RequestBody Organization organization) {
        UUID organizationUUID;
        try {
            organizationUUID = UUID.fromString(organizationId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        organization.setOrganizationId(organizationUUID);
        organization.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(organizationRepository.updateOrganization(organization), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete an organization by organizationId")
    @DeleteMapping("/{organizationId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Organization deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Organization not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteOrganization(@PathVariable String organizationId) {
        UUID organizationUUID;
        try {
            organizationUUID = UUID.fromString(organizationId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!organizationRepository.findOrganizationById(organizationUUID).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        organizationRepository.deleteByOrganizationId(organizationUUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}