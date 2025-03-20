package com.zeilvoortgang.education.controller;

import com.zeilvoortgang.education.entity.VoortgangStatussen;
import com.zeilvoortgang.education.repository.VoortgangStatussenRepository;

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
@RequestMapping("/api/voortgangStatussen")
public class VoortgangStatussenController {

    @Autowired
    private VoortgangStatussenRepository voortgangStatussenRepository;

    @ApiOperation(value = "Get a voortgangStatussen by id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "VoortgangStatussen retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "VoortgangStatussen not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<VoortgangStatussen> getVoortgangStatussenById(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<VoortgangStatussen> voortgangStatussen = voortgangStatussenRepository
                .findVoortgangStatussenById(idUUID);
        if (voortgangStatussen.isPresent()) {
            return new ResponseEntity<>(voortgangStatussen.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ApiOperation(value = "Create a new voortgangStatussen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "VoortgangStatussen created successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Duplicate entry"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<VoortgangStatussen> createVoortgangStatussen(
            @RequestBody VoortgangStatussen voortgangStatussen) {
        voortgangStatussen.setVoortgangStatussenId(UUID.randomUUID());
        voortgangStatussen.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(voortgangStatussenRepository.saveVoortgangStatussen(voortgangStatussen),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a voortgangStatussen by id")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "VoortgangStatussen updated successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "VoortgangStatussen not found"),
            @ApiResponse(code = 409, message = "Duplicate entry"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<VoortgangStatussen> updateVoortgangStatussen(@PathVariable String id,
            @RequestBody VoortgangStatussen voortgangStatussenDetails) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<VoortgangStatussen> voortgangStatussen = voortgangStatussenRepository
                .findVoortgangStatussenById(idUUID);
        if (voortgangStatussen.isPresent()) {
            VoortgangStatussen updatedVoortgangStatussen = voortgangStatussen.get();
            updatedVoortgangStatussen.setLesId(voortgangStatussenDetails.getLesId());
            updatedVoortgangStatussen.setMethodiekId(voortgangStatussenDetails.getMethodiekId());
            updatedVoortgangStatussen.setUserId(voortgangStatussenDetails.getUserId());
            updatedVoortgangStatussen.setProgressId(voortgangStatussenDetails.getProgressId());
            updatedVoortgangStatussen.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
            updatedVoortgangStatussen.setLastUpdateIdentifier(voortgangStatussenDetails.getLastUpdateIdentifier());
            voortgangStatussenRepository.updateVoortgangStatussen(updatedVoortgangStatussen);
            return new ResponseEntity<>(updatedVoortgangStatussen, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete a voortgangStatussen by id")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "VoortgangStatussen deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "VoortgangStatussen not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteVoortgangStatussen(@PathVariable String id) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(id);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<VoortgangStatussen> voortgangStatussen = voortgangStatussenRepository
                .findVoortgangStatussenById(idUUID);
        if (voortgangStatussen.isPresent()) {
            voortgangStatussenRepository.deleteByVoortgangStatussenId(idUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}