package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LeerlijnLesDetails;
import com.zeilvoortgang.education.repository.LeerlijnLesDetailsRepository;

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
@RequestMapping("/api/leerlijnLesDetails")
public class LeerlijnLesDetailsController {

    @Autowired
    private LeerlijnLesDetailsRepository leerlijnLesDetailsRepository;

    @ApiOperation(value = "Get a leerlijnLesDetails by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLesDetails retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLesDetails not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLesDetails> getLeerlijnLesDetailsById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLesDetails> leerlijnLesDetails = leerlijnLesDetailsRepository.findLeerlijnLesDetailsById(idUUID);
        if (leerlijnLesDetails.isPresent()) {
            return new ResponseEntity<>(leerlijnLesDetails.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new leerlijnLesDetails")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLesDetails created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLesDetails> createLeerlijnLesDetails(@RequestBody LeerlijnLesDetails leerlijnLesDetails) {
        leerlijnLesDetails.setLeerlijnLesDetailsId(UUID.randomUUID());
        leerlijnLesDetails.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(leerlijnLesDetailsRepository.saveLeerlijnLesDetails(leerlijnLesDetails), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a leerlijnLesDetails by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLesDetails updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLesDetails not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLesDetails> updateLeerlijnLesDetails(@PathVariable String id, @RequestBody LeerlijnLesDetails leerlijnLesDetailsDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLesDetails> leerlijnLesDetails = leerlijnLesDetailsRepository.findLeerlijnLesDetailsById(idUUID);
        if (leerlijnLesDetails.isPresent()) {
            leerlijnLesDetailsDetails.setLeerlijnLesDetailsId(idUUID);
            leerlijnLesDetailsRepository.updateLeerlijnLesDetails(leerlijnLesDetailsDetails);
            return new ResponseEntity<>(leerlijnLesDetailsDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a leerlijnLesDetails by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLesDetails deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLesDetails not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLeerlijnLesDetails(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLesDetails> leerlijnLesDetails = leerlijnLesDetailsRepository.findLeerlijnLesDetailsById(idUUID);
        if (leerlijnLesDetails.isPresent()) {
            leerlijnLesDetailsRepository.deleteLeerlijnLesDetailsById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}