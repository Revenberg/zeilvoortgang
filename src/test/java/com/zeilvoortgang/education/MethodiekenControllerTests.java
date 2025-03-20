package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Methodieken;
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
class MethodiekenControllerTests {

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
     void testCreateMethodieken() {
        String methodiek = RandomString.make(20);
        String description = RandomString.make(20);
        ResponseEntity<Methodieken> response = GenericCallsEducation.createMethodieken(restTemplate, methodiek,
                description);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateMethodieken() {
        // Create a methodieken first
        ResponseEntity<Methodieken> createResponse = GenericCallsEducation.createMethodieken(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Methodieken createdMethodieken = createResponse.getBody();
        assertThat(createdMethodieken).isNotNull();

        // Update the methodieken's description
        String newDescription = "Updated Test Description";
        createdMethodieken.setDescription(newDescription);
        HttpEntity<Methodieken> requestUpdate = new HttpEntity<>(createdMethodieken);
        ResponseEntity<Methodieken> updateResponse = restTemplate.exchange(
                "/api/methodieken/" + createdMethodieken.getMethodiekId().toString(), HttpMethod.PUT, requestUpdate,
                Methodieken.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(newDescription);

        // Verify the methodieken is updated in the database
        ResponseEntity<Methodieken> getResponse = restTemplate
                .getForEntity("/api/methodieken/" + createdMethodieken.getMethodiekId().toString(), Methodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteMethodieken() {
        // Create a methodieken first
        ResponseEntity<Methodieken> createResponse = GenericCallsEducation.createMethodieken(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Methodieken createdMethodieken = createResponse.getBody();
        assertThat(createdMethodieken).isNotNull();

        // Delete the methodieken
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/methodieken/" + createdMethodieken.getMethodiekId().toString(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the methodieken is deleted
        ResponseEntity<Methodieken> getResponse = restTemplate
                .getForEntity("/api/methodieken/" + createdMethodieken.getMethodiekId().toString(), Methodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetMethodiekenById() {
        // Create a methodieken first
        String methodiek = RandomString.make(20);
        String description = RandomString.make(20);
        ResponseEntity<Methodieken> createResponse = GenericCallsEducation.createMethodieken(restTemplate, methodiek,
                description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Methodieken createdMethodieken = createResponse.getBody();
        assertThat(createdMethodieken).isNotNull();

        // Retrieve the methodieken by ID
        ResponseEntity<Methodieken> getResponse = restTemplate
                .getForEntity("/api/methodieken/" + createdMethodieken.getMethodiekId().toString(), Methodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testMethodiekenNotFound() {
        // Try to retrieve a methodieken that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<Methodieken> getResponse = restTemplate.getForEntity("/api/methodieken/" + nonExistentId,
                Methodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateMethodieken() {
        // Create a methodieken first
        // Create a methodieken first
        ResponseEntity<Methodieken> createResponse = GenericCallsEducation.createMethodieken(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another methodieken with the same ID
        ResponseEntity<Methodieken> duplicateResponse = restTemplate.postForEntity("/api/methodieken",
                createResponse.getBody(), Methodieken.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetMethodiekenByIdWithInvalidId() {
        // Attempt to retrieve a methodieken with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Methodieken> getResponse = restTemplate.getForEntity("/api/methodieken/" + invalidId,
                Methodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteMethodiekenWithInvalidId() {
        // Attempt to delete a methodieken with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/methodieken/" + invalidId, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}