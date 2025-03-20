package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LesStatussen;
import com.zeilvoortgang.education.repository.LesStatussenRepository;

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
@RequestMapping("/api/lesStatussen")
public class LesStatussenController {

    @Autowired
    private LesStatussenRepository lesStatussenRepository;

    @ApiOperation(value = "Get a lesStatussen by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesStatussen retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesStatussen not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesStatussen> getLesStatussenById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesStatussen> lesStatussen = lesStatussenRepository.findLesStatussenById(idUUID);
        if (lesStatussen.isPresent()) {
            return new ResponseEntity<>(lesStatussen.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new lesStatussen")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesStatussen created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesStatussen> createLesStatussen(@RequestBody LesStatussen lesStatussen) {
        lesStatussen.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            lesStatussen.setLesStatusId(UUID.randomUUID());
            lesStatussen.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(lesStatussenRepository.saveLesStatussen(lesStatussen), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a lesStatussen by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesStatussen updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesStatussen not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesStatussen> updateLesStatussen(@PathVariable String id, @RequestBody LesStatussen lesStatussenDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesStatussen> lesStatussen = lesStatussenRepository.findLesStatussenById(idUUID);
        if (lesStatussen.isPresent()) {            
            lesStatussenDetails.setLesStatusId(idUUID);
            lesStatussenDetails.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));            
            lesStatussenRepository.updateLesStatussen(lesStatussenDetails);
            return new ResponseEntity<>(lesStatussenDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a lesStatussen by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesStatussen deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesStatussen not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesStatussen(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesStatussen> lesStatussen = lesStatussenRepository.findLesStatussenById(idUUID);
        if (lesStatussen.isPresent()) {
            lesStatussenRepository.deleteByLesStatusId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}