package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.LesCursisten;
import com.zeilvoortgang.usermanagement.GenericCallsUsermanagement;
import com.zeilvoortgang.usermanagement.entity.User;

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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class LesCursistenControllerTests {

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
     void testCreateLesCursisten() {
        // Create a lesCursisten
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID cursistId = user.getBody().getUserId();
        
        ResponseEntity<LesCursisten> response = GenericCallsEducation.createLesCursisten(restTemplate, lesId, cursistId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLesId()).isEqualTo(lesId);
        assertThat(response.getBody().getCursistId()).isEqualTo(cursistId);
    }


    @SuppressWarnings("null")
    @Test 
     void testDeleteLesCursisten() {
        // Create a lesCursisten
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID cursistId = user.getBody().getUserId();
        
        ResponseEntity<LesCursisten> createResponse = GenericCallsEducation.createLesCursisten(restTemplate, lesId, cursistId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesCursisten createdLesCursisten = createResponse.getBody();
        assertThat(createdLesCursisten).isNotNull();

        // Delete the lesCursisten
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/lesCursisten/" + createdLesCursisten.getLesCursistenId().toString(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the lesCursisten is deleted
        ResponseEntity<LesCursisten> getResponse = restTemplate.getForEntity(
                "/api/lesCursisten/" + createdLesCursisten.getLesCursistenId().toString(), LesCursisten.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesCursistenById() {
        // Create a lesCursisten
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID cursistId = user.getBody().getUserId();
        
        ResponseEntity<LesCursisten> createResponse = GenericCallsEducation.createLesCursisten(restTemplate, lesId, cursistId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesCursisten createdLesCursisten = createResponse.getBody();
        assertThat(createdLesCursisten).isNotNull();

        // Retrieve the lesCursisten by ID
        ResponseEntity<LesCursisten> getResponse = restTemplate.getForEntity(
                "/api/lesCursisten/" + createdLesCursisten.getLesCursistenId().toString(), LesCursisten.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLesId()).isEqualTo(lesId);
        assertThat(getResponse.getBody().getCursistId()).isEqualTo(cursistId);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesCursistenNotFound() {
        // Try to retrieve a lesCursisten that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<LesCursisten> getResponse = restTemplate.getForEntity("/api/lesCursisten/" + nonExistentId.toString(),
                LesCursisten.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLesCursisten() {
        // Create a lesCursisten first
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID cursistId = user.getBody().getUserId();
        
        ResponseEntity<LesCursisten> createResponse = GenericCallsEducation.createLesCursisten(restTemplate, lesId, cursistId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another lesCursisten with the same ID
        ResponseEntity<LesCursisten> duplicateResponse = restTemplate.postForEntity("/api/lesCursisten", createResponse.getBody(),
                LesCursisten.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesCursistenByIdWithInvalidId() {
        // Attempt to retrieve a lesCursisten with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LesCursisten> getResponse = restTemplate.getForEntity("/api/lesCursisten/" + invalidId,
                LesCursisten.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesCursistenWithInvalidId() {
        // Attempt to delete a lesCursisten with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesCursisten/" + invalidId, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}