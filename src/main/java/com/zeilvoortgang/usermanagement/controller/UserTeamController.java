package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.UserTeam;
import com.zeilvoortgang.usermanagement.repository.UserTeamRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user-teams")
public class UserTeamController {

    @Autowired
    private UserTeamRepository userTeamRepository;

    @ApiOperation(value = "Get a user team by userTeamId")
    @GetMapping("/{userTeamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User team retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User team not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserTeam> getUserTeamById(@PathVariable String userTeamId) {
        UUID idUUID;
        try {
            idUUID = UUID.fromString(userTeamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return userTeamRepository.findUserTeamById(idUUID)
                .map(userTeam -> new ResponseEntity<>(userTeam, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Get a user team by userId")
    @GetMapping("/user/{userId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User team retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User team not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<UserTeam>> findUserTeamByUserId(@PathVariable String userId) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserTeam> userTeams = userTeamRepository.findUserTeamByUserId(userUUID);
        if (userTeams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userTeams);
    }

    @ApiOperation(value = "Get a user team by teamId")
    @GetMapping("/team/{teamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User team retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User team not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<List<UserTeam>> findUserTeamByTeamId(@PathVariable String teamId) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserTeam> userTeams = userTeamRepository.findUserTeamByTeamId(teamUUID);
        if (userTeams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userTeams);
    }

    @ApiOperation(value = "Create a new user team")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User team created successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 409, message = "Duplicate user team"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<UserTeam> createUserTeam(@RequestBody UserTeam userTeam) {
        userTeam.setUserTeamId(UUID.randomUUID());
        userTeam.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(userTeamRepository.saveUserTeam(userTeam), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete user team by userteamId")
    @DeleteMapping("/{userteamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User team deleted successfully"),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 404, message = "User team not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteUserTeam(@PathVariable String userteamId) {
        UUID userTeamUUID;
        try {
            userTeamUUID = UUID.fromString(userteamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            userTeamRepository.deleteByUserTeamId(userTeamUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}