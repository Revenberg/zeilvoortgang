package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LesTrainers;
import com.zeilvoortgang.education.repository.LesTrainersRepository;

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
@RequestMapping("/api/lesTrainers")
public class LesTrainersController {

    @Autowired
    private LesTrainersRepository lesTrainersRepository;

    @ApiOperation(value = "Get a lesTrainers by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesTrainers retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesTrainers not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesTrainers> getLesTrainersById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesTrainers> lesTrainers = lesTrainersRepository.findLesTrainersById(idUUID);
        if (lesTrainers.isPresent()) {
            return new ResponseEntity<>(lesTrainers.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new lesTrainers")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesTrainers created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesTrainers> createLesTrainers(@RequestBody LesTrainers lesTrainers) {
        lesTrainers.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            lesTrainers.setLesTrainersId(UUID.randomUUID());
            lesTrainers.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(lesTrainersRepository.saveLesTrainers(lesTrainers), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a lesTrainers by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesTrainers updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesTrainers not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesTrainers> updateLesTrainers(@PathVariable String id, @RequestBody LesTrainers lesTrainersDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesTrainers> lesTrainers = lesTrainersRepository.findLesTrainersById(idUUID);
        if (lesTrainers.isPresent()) {
            lesTrainersDetails.setLesTrainersId(idUUID);
            lesTrainersDetails.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));

            return new ResponseEntity<>(lesTrainersDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a lesTrainers by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesTrainers deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesTrainers not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesTrainers(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesTrainers> lesTrainers = lesTrainersRepository.findLesTrainersById(idUUID);
        if (lesTrainers.isPresent()) {
            lesTrainersRepository.deleteByLesTrainersId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}