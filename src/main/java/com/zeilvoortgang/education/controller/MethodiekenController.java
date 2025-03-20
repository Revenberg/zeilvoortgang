package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.Methodieken;
import com.zeilvoortgang.education.repository.MethodiekenRepository;

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
@RequestMapping("/api/methodieken")
public class MethodiekenController {

    @Autowired
    private MethodiekenRepository methodiekenRepository;

    @ApiOperation(value = "Get a methodiek by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Methodiek retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Methodiek not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Methodieken> getMethodiekById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Methodieken> methodiek = methodiekenRepository.findMethodiekById(idUUID);
        if (methodiek.isPresent()) {
            return new ResponseEntity<>(methodiek.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new methodiek")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Methodiek created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Methodieken> createMethodiek(@RequestBody Methodieken methodiek) {
        methodiek.setMethodiekId(UUID.randomUUID());
        methodiek.setLastUpdateTMS(new java.sql.Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(methodiekenRepository.saveMethodiek(methodiek), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a methodiek by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Methodiek updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Methodiek not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Methodieken> updateMethodiek(@PathVariable String id, @RequestBody Methodieken methodiek) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Methodieken> methodiekCheck = methodiekenRepository.findMethodiekById(idUUID);
        if (methodiekCheck.isPresent()) {
            methodiek.setLastUpdateTMS(new java.sql.Timestamp(System.currentTimeMillis()));
            methodiekenRepository.updateMethodiek(methodiek);
            return new ResponseEntity<>(methodiek, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a methodiek by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Methodiek deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Methodiek not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteMethodiek(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Methodieken> methodiek = methodiekenRepository.findMethodiekById(idUUID);
        if (methodiek.isPresent()) {
            methodiekenRepository.deleteMethodiekById(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}