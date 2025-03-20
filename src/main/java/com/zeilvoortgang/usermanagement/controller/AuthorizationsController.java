package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.Authorizations;
import com.zeilvoortgang.usermanagement.repository.AuthorizationsRepository;

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
@RequestMapping("/api/authorizations")
public class AuthorizationsController {

    @Autowired
    private AuthorizationsRepository authorizationsRepository;

    @ApiOperation(value = "Get an authorizations by authorizationsId")
    @GetMapping("/{authorizationsId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorizations retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorizations not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Authorizations> getAuthorizationsById(@PathVariable String authorizationsId) {
        UUID authorizationsUUID;
        try {
            authorizationsUUID = UUID.fromString(authorizationsId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return authorizationsRepository.findAuthorizationById(authorizationsUUID)
                .map(auth -> new ResponseEntity<>(auth, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new authorizations")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorizations created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate authorizations"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Authorizations> createAuthorizations(@RequestBody Authorizations authorizations) {
        authorizations.setAuthorizationId(UUID.randomUUID());
        authorizations.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationsRepository.saveAuthorization(authorizations), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update an authorizations by authorizationsId")
    @PutMapping("/{authorizationsId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorizations updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorizations not found"),
        @ApiResponse(code = 409, message = "Duplicate authorizations"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Authorizations> updateAuthorizations(@PathVariable String authorizationsId, @RequestBody Authorizations authorizations) {
        UUID authorizationsUUID;
        try {
            authorizationsUUID = UUID.fromString(authorizationsId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        authorizations.setAuthorizationId(authorizationsUUID);
        authorizations.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(authorizationsRepository.updateAuthorization(authorizations), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete an authorizations by authorizationsId")
    @DeleteMapping("/{authorizationsId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Authorizations deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Authorizations not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteAuthorizations(@PathVariable String authorizationsId) {
        UUID authorizationsUUID;
        try {
            authorizationsUUID = UUID.fromString(authorizationsId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            authorizationsRepository.deleteAuthorizationById(authorizationsUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}