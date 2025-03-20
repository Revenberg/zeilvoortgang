package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Les;
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
class LesControllerTests {

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
     void testCreateLes() {
        // Create a les
        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Les> response = GenericCallsEducation.createLes(restTemplate, title, description);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(title);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLes() {
        // Create a les first
        ResponseEntity<Les> createResponse = GenericCallsEducation.createLes(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Les createdLes = createResponse.getBody();
        assertThat(createdLes).isNotNull();

        // Update the les's title
        String newTitle = "Updated Test Les";
        createdLes.setTitle(newTitle);
        HttpEntity<Les> requestUpdate = new HttpEntity<>(createdLes);
        ResponseEntity<Les> updateResponse = restTemplate.exchange("/api/les/" + createdLes.getLesId().toString(),
                HttpMethod.PUT, requestUpdate, Les.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getTitle()).isEqualTo(newTitle);

        // Verify the les is updated in the database
        ResponseEntity<Les> getResponse = restTemplate.getForEntity("/api/les/" + createdLes.getLesId().toString(),
                Les.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTitle()).isEqualTo(newTitle);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLes() {
        // Create a les first
        // Create a les first
        ResponseEntity<Les> createResponse = GenericCallsEducation.createLes(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Les createdLes = createResponse.getBody();
        assertThat(createdLes).isNotNull();

        // Delete the les
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/les/" + createdLes.getLesId().toString(),
                HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the les is deleted
        ResponseEntity<Les> getResponse = restTemplate.getForEntity("/api/les/" + createdLes.getLesId().toString(),
                Les.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesById() {
        // Create a les first
        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Les> createResponse = GenericCallsEducation.createLes(restTemplate, title, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Les createdLes = createResponse.getBody();
        assertThat(createdLes).isNotNull();

        // Retrieve the les by ID
        ResponseEntity<Les> getResponse = restTemplate.getForEntity("/api/les/" + createdLes.getLesId().toString(),
                Les.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();        
        assertThat(getResponse.getBody().getTitle()).isEqualTo(title);
        assertThat(getResponse.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesNotFound() {
        // Try to retrieve a les that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<Les> getResponse = restTemplate.getForEntity("/api/les/" + nonExistentId, Les.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
/* 
    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLes() {
        // Create a les first
        ResponseEntity<Les> createResponse = GenericCallsEducation.createLes(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another les with the same ID
        ResponseEntity<Les> duplicateResponse = restTemplate.postForEntity("/api/les", createResponse.getBody(), Les.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
    */
    @SuppressWarnings("null")
    @Test 
     void testGetLesByIdWithInvalidId() {
        // Attempt to retrieve a les with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Les> getResponse = restTemplate.getForEntity("/api/les/" + invalidId, Les.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesWithInvalidId() {
        // Attempt to delete a les with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/les/" + invalidId, HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}