package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.UserLevels;
import com.zeilvoortgang.education.repository.UserLevelsRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userLevels")
public class UserLevelsController {

    @Autowired
    private UserLevelsRepository userLevelsRepository;

    @ApiOperation(value = "Get a userLevels by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UserLevels retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "UserLevels not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserLevels> getUserLevelsById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
         Optional<UserLevels> userLevels = userLevelsRepository.findUserLevelsById(idUUID);
        if (userLevels.isPresent()) {
            return new ResponseEntity<>(userLevels.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new userLevels")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UserLevels created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserLevels> createUserLevels(@RequestBody UserLevels userLevels) {
        userLevels.setUserLevelsId(UUID.randomUUID());
        userLevels.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));        
        try {
            return new ResponseEntity<>(userLevelsRepository.saveUserLevels(userLevels), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a userLevels by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UserLevels updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "UserLevels not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserLevels> updateUserLevels(@PathVariable String id, @RequestBody UserLevels userLevelsDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<UserLevels> userLevels = userLevelsRepository.findUserLevelsById(idUUID);
        if (userLevels.isPresent()) {
            UserLevels updatedUserLevels = userLevels.get();
            updatedUserLevels.setUserId(userLevelsDetails.getUserId());
            updatedUserLevels.setLevelId(userLevelsDetails.getLevelId());
            updatedUserLevels.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            updatedUserLevels.setLastUpdateIdentifier(userLevelsDetails.getLastUpdateIdentifier());
            userLevelsRepository.updateUserLevels(updatedUserLevels);
            return new ResponseEntity<>(updatedUserLevels, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a userLevels by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "UserLevels deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "UserLevels not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteUserLevels(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<UserLevels> userLevels = userLevelsRepository.findUserLevelsById(idUUID);
        if (userLevels.isPresent()) {
            userLevelsRepository.deleteByUserLevelsId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}