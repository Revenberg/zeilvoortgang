package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Levels;
import com.zeilvoortgang.usermanagement.GenericCallsUsermanagement;
import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.education.entity.UserLevels;
import com.zeilvoortgang.education.entity.VoortgangStatussen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangUserLevelsApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        // Initialize TestRestTemplate with the correct port
        this.restTemplate = new TestRestTemplate(
                restTemplateBuilder
                        .basicAuthentication("user", "password")
                        .rootUri("http://localhost:" + port));
    }

    @SuppressWarnings("null")
    @Test
    void testCreateUserLevels() {
        // Create a user level first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);        
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<UserLevels> response = GenericCallsEducation.createUserLevels(restTemplate, userId, levelId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(userId);
        assertThat(response.getBody().getLevelId()).isEqualTo(levelId);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateUserLevels() {
        // Create a user level first
        ResponseEntity<UserLevels> createResponse = GenericCallsEducation.createUserLevels(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserLevels createdUserLevels = createResponse.getBody();
        assertThat(createdUserLevels).isNotNull();

        // Update the user level's last update level        
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);        
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID levelId = level.getBody().getLevelId();

        createdUserLevels.setLevelId(levelId);
        HttpEntity<UserLevels> requestUpdate = new HttpEntity<>(createdUserLevels);    

        ResponseEntity<UserLevels> updateResponse = restTemplate.exchange(
                "/api/userLevels/" + createdUserLevels.getUserLevelsId(), HttpMethod.PUT, requestUpdate, UserLevels.class);
  
                assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getLevelId()).isEqualTo(levelId);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteUserLevels() {
        // Create a user level first
        ResponseEntity<UserLevels> createResponse = GenericCallsEducation.createUserLevels(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserLevels createdUserLevels = createResponse.getBody();
        assertThat(createdUserLevels).isNotNull();

        // Delete the user level
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/userLevels/" + createdUserLevels.getUserLevelsId().toString(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the user level is deleted
        ResponseEntity<UserLevels> getResponse = restTemplate
                .getForEntity("/api/userLevels/" + createdUserLevels.getUserLevelsId().toString(), UserLevels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testGetUserLevelsById() {
        // Create a user level first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);        
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<UserLevels> createResponse = GenericCallsEducation.createUserLevels(restTemplate, userId, levelId);
        
        // Retrieve the user level by ID
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserLevels createdUserLevels = createResponse.getBody();
        assertThat(createdUserLevels).isNotNull();

        // Retrieve the user level by ID
        ResponseEntity<UserLevels> getResponse = restTemplate
                .getForEntity("/api/userLevels/" + createdUserLevels.getUserLevelsId().toString(), UserLevels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getUserId()).isEqualTo(userId);
        assertThat(getResponse.getBody().getLevelId()).isEqualTo(levelId);
    }

    @SuppressWarnings("null")
    @Test
    void testUserLevelsNotFound() {
        // Try to retrieve a user level that does not exist
        UUID nonExistentUserLevelsId = UUID.randomUUID();
        ResponseEntity<UserLevels> getResponse = restTemplate.getForEntity("/api/userLevels/" + nonExistentUserLevelsId,
                UserLevels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateDuplicateUserLevels() {
        // Create a user level first
        // Create a user level first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);        
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<UserLevels> createResponse = GenericCallsEducation.createUserLevels(restTemplate, userId, levelId);                
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<UserLevels> duplicateResponse = GenericCallsEducation.createUserLevels(restTemplate, userId, levelId);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test
    void testGetUserLevelsByIdWithInvalidUUID() {
        // Attempt to retrieve a user level with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<UserLevels> getResponse = restTemplate.getForEntity("/api/userLevels/" + invalidUUID,
                UserLevels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteUserLevelsWithInvalidUUID() {
        // Attempt to delete a user level with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/userLevels/" + invalidUUID, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateUserLevelsWithInvalidUUID() {
        // Attempt to update a user level with an invalid UUID
        String invalidUUID = "invalid-uuid";
        UserLevels userLevels = new UserLevels();
        userLevels.setUserId(UUID.randomUUID());
        userLevels.setLevelId(UUID.randomUUID());
        HttpEntity<UserLevels> requestUpdate = new HttpEntity<>(userLevels);
        ResponseEntity<UserLevels> updateResponse = restTemplate.exchange("/api/userLevels/" + invalidUUID,
                HttpMethod.PUT, requestUpdate, UserLevels.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}