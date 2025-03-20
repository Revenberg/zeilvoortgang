package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.ProgressStatussen;
import com.zeilvoortgang.education.repository.ProgressStatussenRepository;

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
@RequestMapping("/api/progressStatussen")
public class ProgressStatussenController {

    @Autowired
    private ProgressStatussenRepository progressStatussenRepository;

    @ApiOperation(value = "Get a progressStatussen by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ProgressStatussen retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "ProgressStatussen not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<ProgressStatussen> getProgressStatussenById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ProgressStatussen> progressStatussen = progressStatussenRepository.findProgressStatussenById(idUUID);
        if (progressStatussen.isPresent()) {
            return new ResponseEntity<>(progressStatussen.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new progressStatussen")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ProgressStatussen created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<ProgressStatussen> createProgressStatussen(@RequestBody ProgressStatussen progressStatussen) {
        progressStatussen.setProgressStatusId(UUID.randomUUID());
        try {
            return new ResponseEntity<>(progressStatussenRepository.saveProgressStatussen(progressStatussen), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a progressStatussen by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ProgressStatussen updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "ProgressStatussen not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<ProgressStatussen> updateProgressStatussen(@PathVariable String id, @RequestBody ProgressStatussen progressStatussenDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ProgressStatussen> progressStatussen = progressStatussenRepository.findProgressStatussenById(idUUID);
        if (progressStatussen.isPresent()) {
            progressStatussenDetails.setProgressStatusId(idUUID);
            progressStatussen = Optional.ofNullable(progressStatussenRepository.updateProgressStatussen(progressStatussenDetails));

            return new ResponseEntity<>(progressStatussenDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a progressStatussen by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ProgressStatussen deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "ProgressStatussen not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteProgressStatussen(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<ProgressStatussen> progressStatussen = progressStatussenRepository.findProgressStatussenById(idUUID);
        if (progressStatussen.isPresent()) {
            progressStatussenRepository.deleteProgressStatussenById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}