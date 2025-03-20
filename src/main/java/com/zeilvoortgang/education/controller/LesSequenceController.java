package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.LesSequence;
import com.zeilvoortgang.education.repository.LesSequenceRepository;

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
@RequestMapping("/api/lesSequence")
public class LesSequenceController {

    @Autowired
    private LesSequenceRepository lesSequenceRepository;

    @ApiOperation(value = "Get a lesSequence by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesSequence retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesSequence not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesSequence> getLesSequenceById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<LesSequence> lesSequence = lesSequenceRepository.findLesSequenceById(idUUID);
        if (lesSequence.isPresent()) {
            return new ResponseEntity<>(lesSequence.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new lesSequence")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesSequence created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesSequence> createLesSequence(@RequestBody LesSequence lesSequence) {
        lesSequence.setLesSequenceId(UUID.randomUUID());
        lesSequence.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(lesSequenceRepository.saveLesSequence(lesSequence), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a lesSequence by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesSequence updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesSequence not found"),
        @ApiResponse(code = 409, message = "Duplicate entry"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<LesSequence> updateLesSequence(@PathVariable String id, @RequestBody LesSequence lesSequenceDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
                Optional<LesSequence> lesSequence = lesSequenceRepository.findLesSequenceById(idUUID);
        if (lesSequence.isPresent()) {
            LesSequence updatedLesSequence = lesSequence.get();
            updatedLesSequence.setLesSequenceId(idUUID);
            updatedLesSequence.setTitle(lesSequenceDetails.getTitle());
            updatedLesSequence.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            updatedLesSequence.setLastUpdateIdentifier(lesSequenceDetails.getLastUpdateIdentifier());
            lesSequenceRepository.updateLesSequence(updatedLesSequence);
            return new ResponseEntity<>(updatedLesSequence, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a lesSequence by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "LesSequence deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "LesSequence not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteLesSequence(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<LesSequence> lesSequence = lesSequenceRepository.findLesSequenceById(idUUID);
        if (lesSequence.isPresent()) {
            lesSequenceRepository.deleteByLesSequenceId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}