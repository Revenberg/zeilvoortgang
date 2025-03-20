package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.Levels;
import com.zeilvoortgang.education.repository.LevelsRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/levels")
public class LevelsController {

    @Autowired
    private LevelsRepository levelsRepository;

    @ApiOperation(value = "Get a level by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Level retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Level not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Levels> getLevelById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Levels> level = levelsRepository.findLevelById(idUUID);
        if (level.isPresent()) {
            return new ResponseEntity<>(level.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new level")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Level created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Levels> createLevel(@RequestBody Levels level) {
        level.setLevelId(UUID.randomUUID());
        level.setLastUpdateTMS(new java.sql.Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(levelsRepository.saveLevel(level), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a level by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Level updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Level not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Levels> updateLevel(@PathVariable String id, @RequestBody Levels level) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Levels> levelCheck = levelsRepository.findLevelById(idUUID);
        if (levelCheck.isPresent()) {
            level.setLevelId(idUUID);
            level.setLastUpdateTMS(new java.sql.Timestamp(System.currentTimeMillis()));
            levelsRepository.updateLevel(level);
            return new ResponseEntity<>(level, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a level by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Level deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Level not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLevel(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Levels> level = levelsRepository.findLevelById(idUUID);
        if (level.isPresent()) {
            levelsRepository.deleteLevelById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}