package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.ProgressStatussen;
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
class ProgressStatussenControllerTests {

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
     void testCreateProgressStatussen() {
        String description = RandomString.make(1);
        String longDescription = RandomString.make(20);
        ResponseEntity<ProgressStatussen> response = GenericCallsEducation.createProgressStatussen(restTemplate,
                description, longDescription);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertThat(response.getBody().getLongDescription()).isEqualTo(longDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateProgressStatussen() {
        // Create a progressStatussen first
        ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation.createProgressStatussen(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProgressStatussen createdProgressStatussen = createResponse.getBody();
        assertThat(createdProgressStatussen).isNotNull();

        // Update the progressStatussen's long description
        String newLongDescription = RandomString.make(20);
        createdProgressStatussen.setLongDescription(newLongDescription);
        HttpEntity<ProgressStatussen> requestUpdate = new HttpEntity<>(createdProgressStatussen);
        ResponseEntity<ProgressStatussen> updateResponse = restTemplate.exchange(
                "/api/progressStatussen/" + createdProgressStatussen.getProgressStatusId().toString(), HttpMethod.PUT,
                requestUpdate, ProgressStatussen.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getLongDescription()).isEqualTo(newLongDescription);

        // Verify the progressStatussen is updated in the database
        ResponseEntity<ProgressStatussen> getResponse = restTemplate.getForEntity(
                "/api/progressStatussen/" + createdProgressStatussen.getProgressStatusId().toString(), ProgressStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLongDescription()).isEqualTo(newLongDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteProgressStatussen() {
        // Create a progressStatussen first
        ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        
        ProgressStatussen createdProgressStatussen = createResponse.getBody();
        assertThat(createdProgressStatussen).isNotNull();

        // Delete the progressStatussen
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/progressStatussen/" + createdProgressStatussen.getProgressStatusId().toString(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the progressStatussen is deleted
        ResponseEntity<ProgressStatussen> getResponse = restTemplate.getForEntity(
                "/api/progressStatussen/" + createdProgressStatussen.getProgressStatusId().toString(), ProgressStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetProgressStatussenById() {
        String description = RandomString.make(10);
        String longDescription = RandomString.make(20);
        ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation.createProgressStatussen(restTemplate,
                description, longDescription);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProgressStatussen createdProgressStatussen = createResponse.getBody();
        assertThat(createdProgressStatussen).isNotNull();

        // Retrieve the progressStatussen by ID
        ResponseEntity<ProgressStatussen> getResponse = restTemplate.getForEntity(
                "/api/progressStatussen/" + createdProgressStatussen.getProgressStatusId().toString(), ProgressStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(description);
        assertThat(getResponse.getBody().getLongDescription()).isEqualTo(longDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testProgressStatussenNotFound() {
        // Try to retrieve a progressStatussen that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<ProgressStatussen> getResponse = restTemplate
                .getForEntity("/api/progressStatussen/" + nonExistentId.toString(), ProgressStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test 
     void testCreateDuplicateProgressStatussen() {
        // Create a progressStatussen first
        ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation.createProgressStatussen(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another progressStatussen with the same ID
        ResponseEntity<ProgressStatussen> duplicateResponse = restTemplate.postForEntity("/api/progressStatussen",
                createResponse, ProgressStatussen.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test 
     void testGetProgressStatussenByIdWithInvalidId() {
        // Attempt to retrieve a progressStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<ProgressStatussen> getResponse = restTemplate.getForEntity("/api/progressStatussen/" + invalidId,
                ProgressStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test 
     void testDeleteProgressStatussenWithInvalidId() {
        // Attempt to delete a progressStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/progressStatussen/" + invalidId,
                HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}