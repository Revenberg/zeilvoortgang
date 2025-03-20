package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Levels;
import net.bytebuddy.utility.RandomString;

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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class LevelsControllerTests {

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
                .rootUri("http://localhost:" + port)
        );
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateLevels() {

        String description = RandomString.make(20);
        ResponseEntity<Levels> response = GenericCallsEducation.createLevels(restTemplate, description);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLevels() {
        // Create a levels first
        ResponseEntity<Levels> createResponse = GenericCallsEducation.createLevels(restTemplate);
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Levels createdLevels = createResponse.getBody();
        assertThat(createdLevels).isNotNull();

        // Update the levels's description
        String newDescription = RandomString.make(20);
        createdLevels.setDescription(newDescription);
        HttpEntity<Levels> requestUpdate = new HttpEntity<>(createdLevels);
        ResponseEntity<Levels> updateResponse = restTemplate.exchange("/api/levels/" + createdLevels.getLevelId().toString(), HttpMethod.PUT, requestUpdate, Levels.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(newDescription);

        // Verify the levels is updated in the database
        ResponseEntity<Levels> getResponse = restTemplate.getForEntity("/api/levels/" + createdLevels.getLevelId(), Levels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLevels() {
        ResponseEntity<Levels> createResponse = GenericCallsEducation.createLevels(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Levels createdLevels = createResponse.getBody();
        assertThat(createdLevels).isNotNull();

        // Delete the levels
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/levels/" + createdLevels.getLevelId().toString(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the levels is deleted
        ResponseEntity<Levels> getResponse = restTemplate.getForEntity("/api/levels/" + createdLevels.getLevelId().toString(), Levels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLevelsById() {
        // Create a levels first
        String description = RandomString.make(20);
        ResponseEntity<Levels> createResponse = GenericCallsEducation.createLevels(restTemplate, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Levels createdLevels = createResponse.getBody();
        assertThat(createdLevels).isNotNull();

        // Retrieve the levels by ID
        ResponseEntity<Levels> getResponse = restTemplate.getForEntity("/api/levels/" + createdLevels.getLevelId(), Levels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testLevelsNotFound() {
        // Try to retrieve a levels that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<Levels> getResponse = restTemplate.getForEntity("/api/levels/" + nonExistentId, Levels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLevels() {
        // Create a levels first
        ResponseEntity<Levels> createResponse = GenericCallsEducation.createLevels(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another levels with the same ID
        ResponseEntity<Levels> duplicateResponse = restTemplate.postForEntity("/api/levels", createResponse, Levels.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLevelsByIdWithInvalidId() {
        // Attempt to retrieve a levels with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Levels> getResponse = restTemplate.getForEntity("/api/levels/" + invalidId, Levels.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLevelsWithInvalidId() {
        // Attempt to delete a levels with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/levels/" + invalidId, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}