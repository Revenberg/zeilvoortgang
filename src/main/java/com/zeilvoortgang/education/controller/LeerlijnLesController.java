package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LeerlijnLes;
import com.zeilvoortgang.education.repository.LeerlijnLesRepository;

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
@RequestMapping("/api/leerlijnLes")
public class LeerlijnLesController {

    @Autowired
    private LeerlijnLesRepository leerlijnLesRepository;

    @ApiOperation(value = "Get a leerlijnLes by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLes retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLes not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLes> getLeerlijnLesById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLes> leerlijnLes = leerlijnLesRepository.findLeerlijnLesById(idUUID);
        if (leerlijnLes.isPresent()) {
            return new ResponseEntity<>(leerlijnLes.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new leerlijnLes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLes created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLes> createLeerlijnLes(@RequestBody LeerlijnLes leerlijnLes) {
        leerlijnLes.setLeerlijnLesId(UUID.randomUUID());
        leerlijnLes.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(leerlijnLesRepository.saveLeerlijnLes(leerlijnLes), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a leerlijnLes by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLes updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLes not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LeerlijnLes> updateLeerlijnLes(@PathVariable String id, @RequestBody LeerlijnLes leerlijnLesDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLes> leerlijnLes = leerlijnLesRepository.findLeerlijnLesById(idUUID);
        if (leerlijnLes.isPresent()) {
            leerlijnLesDetails.setLeerlijnLesId(idUUID);
            leerlijnLesRepository.updateLeerlijnLes(leerlijnLesDetails);
            return new ResponseEntity<>(leerlijnLesDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a leerlijnLes by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LeerlijnLes deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LeerlijnLes not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLeerlijnLes(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LeerlijnLes> leerlijnLes = leerlijnLesRepository.findLeerlijnLesById(idUUID);
        if (leerlijnLes.isPresent()) {
            leerlijnLesRepository.deleteLeerlijnLesById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}