package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LesMethodieken;
import com.zeilvoortgang.education.repository.LesMethodiekenRepository;

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
@RequestMapping("/api/lesMethodieken")
public class LesMethodiekenController {

    @Autowired
    private LesMethodiekenRepository lesMethodiekenRepository;

    @ApiOperation(value = "Get a lesMethodieken by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesMethodieken retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesMethodieken not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesMethodieken> getLesMethodiekenById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesMethodieken> lesMethodieken = lesMethodiekenRepository.findLesMethodiekenById(idUUID);
        if (lesMethodieken.isPresent()) {
            return new ResponseEntity<>(lesMethodieken.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new lesMethodieken")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesMethodieken created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesMethodieken> createLesMethodieken(@RequestBody LesMethodieken lesMethodieken) {
        lesMethodieken.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            lesMethodieken.setLesMethodiekenId(UUID.randomUUID());
            lesMethodieken.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(lesMethodiekenRepository.saveLesMethodieken(lesMethodieken), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a lesMethodieken by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesMethodieken updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesMethodieken not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesMethodieken> updateLesMethodieken(@PathVariable String id, @RequestBody LesMethodieken lesMethodiekenDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesMethodieken> lesMethodieken = lesMethodiekenRepository.findLesMethodiekenById(idUUID);
        if (lesMethodieken.isPresent()) {
            lesMethodiekenDetails.setLesMethodiekenId(idUUID);
            lesMethodiekenDetails.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            lesMethodiekenRepository.updateLesMethodieken(lesMethodiekenDetails);
            return new ResponseEntity<>(lesMethodiekenDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a lesMethodieken by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesMethodieken deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesMethodieken not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesMethodieken(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesMethodieken> lesMethodieken = lesMethodiekenRepository.findLesMethodiekenById(idUUID);
        if (lesMethodieken.isPresent()) {
            lesMethodiekenRepository.deleteByLesMethodiekenId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}