package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.Team;
import com.zeilvoortgang.usermanagement.exception.DuplicateTeamException;
import com.zeilvoortgang.usermanagement.repository.TeamRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @ApiOperation(value = "Get a team by teamId")
    @GetMapping("/{teamId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Team not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Team> getTeamById(@PathVariable String teamId) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return teamRepository.findTeamById(teamUUID)
                .map(team -> new ResponseEntity<>(team, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new team")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate team"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        team.setTeamId(UUID.randomUUID());
        team.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(teamRepository.saveTeam(team), HttpStatus.OK);
        } catch (DuplicateTeamException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Update a team by teamId")
    @PutMapping("/{teamId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Team not found"),
        @ApiResponse(code = 409, message = "Duplicate team"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Team> updateTeam(@PathVariable String teamId, @RequestBody Team team) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        team.setTeamId(teamUUID);
        team.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(teamRepository.updateTeam(team), HttpStatus.OK);
        } catch (DuplicateTeamException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete a team by teamId")
    @DeleteMapping("/{teamId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Team deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "Team not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteTeam(@PathVariable String teamId) {
        UUID teamUUID;
        try {
            teamUUID = UUID.fromString(teamId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!teamRepository.findTeamById(teamUUID).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teamRepository.deleteByTeamId(teamUUID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}