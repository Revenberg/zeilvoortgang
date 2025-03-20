package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.Leerlijn;
import com.zeilvoortgang.education.repository.LeerlijnRepository;

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
@RequestMapping("/api/leerlijn")
public class LeerlijnController {

    @Autowired
    private LeerlijnRepository leerlijnRepository;

    @ApiOperation(value = "Get a leerlijn by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Leerlijn retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Leerlijn not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Leerlijn> getLeerlijnById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Leerlijn> leerlijn = leerlijnRepository.findLeerlijnById(idUUID);
        if (leerlijn.isPresent()) {
            return new ResponseEntity<>(leerlijn.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new leerlijn")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Leerlijn created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Leerlijn> createLeerlijn(@RequestBody Leerlijn leerlijn) {
        leerlijn.setLeerlijnId(UUID.randomUUID());
        leerlijn.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(leerlijnRepository.saveLeerlijn(leerlijn), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a leerlijn by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Leerlijn updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Leerlijn not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Leerlijn> updateLeerlijn(@PathVariable String id, @RequestBody Leerlijn leerlijnDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Leerlijn> leerlijn = leerlijnRepository.findLeerlijnById(idUUID);
        if (leerlijn.isPresent()) {
            leerlijnDetails.setLeerlijnId(idUUID);
            leerlijnRepository.updateLeerlijn(leerlijnDetails);
            return new ResponseEntity<>(leerlijnDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a leerlijn by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Leerlijn deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Leerlijn not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLeerlijn(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Leerlijn> leerlijn = leerlijnRepository.findLeerlijnById(idUUID);
        if (leerlijn.isPresent()) {
            leerlijnRepository.deleteLeerlijnById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}