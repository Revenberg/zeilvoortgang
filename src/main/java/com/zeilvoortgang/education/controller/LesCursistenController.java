package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LesCursisten;
import com.zeilvoortgang.education.repository.LesCursistenRepository;

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
@RequestMapping("/api/lesCursisten")
public class LesCursistenController {

    @Autowired
    private LesCursistenRepository lesCursistenRepository;

    @ApiOperation(value = "Get a lesCursisten by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "LesCursisten retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "LesCursisten not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesCursisten> getLesCursistenById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesCursisten> lesCursisten = lesCursistenRepository.findLesCursistenById(idUUID);
        if (lesCursisten.isPresent()) {
            return new ResponseEntity<>(lesCursisten.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new lesCursisten")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "LesCursisten created successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Duplicate entry"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesCursisten> createLesCursisten(@RequestBody LesCursisten lesCursisten) {
        lesCursisten.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            lesCursisten.setLesCursistenId(UUID.randomUUID());
            lesCursisten.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(lesCursistenRepository.saveLesCursisten(lesCursisten), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a lesCursisten by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "LesCursisten deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "LesCursisten not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesCursisten(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesCursisten> lesCursisten = lesCursistenRepository.findLesCursistenById(idUUID);
        if (lesCursisten.isPresent()) {
            lesCursistenRepository.deleteByLesCursistenId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}