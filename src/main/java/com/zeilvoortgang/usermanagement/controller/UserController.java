package com.zeilvoortgang.usermanagement.controller;

import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.usermanagement.repository.UserRepository;

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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Get a user by userId")
    @GetMapping("/{userId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User retrieved successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return userRepository.findUserById(userUUID)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @ApiOperation(value = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User created successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 409, message = "Duplicate username or email"),
        @ApiResponse(code = 500, message = "Internal server error")
    })    
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserId(UUID.randomUUID());
        user.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(userRepository.saveUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Update a user by userId")
    @PutMapping("/{userId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User updated successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 409, message = "Duplicate username or email"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
       
        user.setUserId(userUUID);
        user.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        try {
            return new ResponseEntity<>(userRepository.updateUser(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @ApiOperation(value = "Delete a user by userId")
    @DeleteMapping("/{userId}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "User deleted successfully"),
        @ApiResponse(code = 400, message = "Invalid input"),
        @ApiResponse(code = 404, message = "User not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        UUID userUUID;
        try {
            userUUID = UUID.fromString(userId);
        } catch (java.lang.IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            userRepository.deleteByUserId(userUUID);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}