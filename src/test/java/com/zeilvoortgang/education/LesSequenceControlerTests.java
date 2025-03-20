package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.LesSequence;

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
class LesSequenceControlerTests {

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
     void testCreateLesSequence() {
        String title = RandomString.make(20);
        ResponseEntity<LesSequence> response = GenericCallsEducation.createLesSequence(restTemplate, title);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTitle()).isEqualTo(title);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLesSequence() {
        // Create a lesSequence first
        String title = RandomString.make(20);
        ResponseEntity<LesSequence> createResponse = GenericCallsEducation.createLesSequence(restTemplate, title);
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesSequence createdLesSequence = createResponse.getBody();
        assertThat(createdLesSequence).isNotNull();

        // Update the lesSequence's title
        String newTitle = RandomString.make(20);
        
        createdLesSequence.setTitle(newTitle);
        HttpEntity<LesSequence> requestUpdate = new HttpEntity<>(createdLesSequence);
        requestUpdate.getBody().setTitle(newTitle);

        ResponseEntity<LesSequence> updateResponse = restTemplate.exchange("/api/lesSequence/" + createdLesSequence.getLesSequenceId().toString(), HttpMethod.PUT, requestUpdate, LesSequence.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getTitle()).isEqualTo(newTitle);

        // Verify the lesSequence is updated in the database
        ResponseEntity<LesSequence> getResponse = restTemplate.getForEntity("/api/lesSequence/" + createdLesSequence.getLesSequenceId().toString(), LesSequence.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTitle()).isEqualTo(newTitle);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesSequence() {
        // Create a lesSequence first
        String title = RandomString.make(20);
        ResponseEntity<LesSequence> createResponse = GenericCallsEducation.createLesSequence(restTemplate, title);
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesSequence createdLesSequence = createResponse.getBody();
        assertThat(createdLesSequence).isNotNull();

        // Delete the lesSequence
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesSequence/" + createdLesSequence.getLesSequenceId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the lesSequence is deleted
        ResponseEntity<LesSequence> getResponse = restTemplate.getForEntity("/api/lesSequence/" + createdLesSequence.getLesSequenceId(), LesSequence.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesSequenceById() {
        // Create a lesSequence first
        String title = RandomString.make(20);
        ResponseEntity<LesSequence> createResponse = GenericCallsEducation.createLesSequence(restTemplate, title);
        
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesSequence createdLesSequence = createResponse.getBody();
        assertThat(createdLesSequence).isNotNull();

        // Retrieve the lesSequence by ID
        ResponseEntity<LesSequence> getResponse = restTemplate.getForEntity("/api/lesSequence/" + createdLesSequence.getLesSequenceId(), LesSequence.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTitle()).isEqualTo(title);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesSequenceNotFound() {
        // Try to retrieve a lesSequence that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<LesSequence> getResponse = restTemplate.getForEntity("/api/lesSequence/" + nonExistentId, LesSequence.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLesSequence() {
        // Create a lesSequence first
        String title = RandomString.make(20);
        ResponseEntity<LesSequence> createResponse = GenericCallsEducation.createLesSequence(restTemplate, title);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another lesSequence with the same title
        ResponseEntity<LesSequence> duplicateResponse = restTemplate.postForEntity("/api/lesSequence", createResponse.getBody(), LesSequence.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesSequenceByIdWithInvalidId() {
        // Attempt to retrieve a lesSequence with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LesSequence> getResponse = restTemplate.getForEntity("/api/lesSequence/" + invalidId, LesSequence.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesSequenceWithInvalidId() {
        // Attempt to delete a lesSequence with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesSequence/" + invalidId, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}