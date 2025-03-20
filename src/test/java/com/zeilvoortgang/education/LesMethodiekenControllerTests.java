package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Les;
import com.zeilvoortgang.education.entity.LesMethodieken;
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
class LesMethodiekenControllerTests {

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
     void testCreateLesMethodieken() {
        // Create a lesMethodieken first
        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        UUID doelId = GenericCallsEducation.getGenericLevelId(restTemplate);

        String description = RandomString.make(20);
        ResponseEntity<LesMethodieken> response = GenericCallsEducation.createLesMethodieken(restTemplate,lesId, methodiekId, doelId, description);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLesId()).isEqualTo(lesId);
        assertThat(response.getBody().getMethodiekId()).isEqualTo(methodiekId);
        assertThat(response.getBody().getDoelId()).isEqualTo(doelId);
        assertThat(response.getBody().getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLesMethodieken() {
        // Create a lesMethodieken first
        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        UUID doelId = GenericCallsEducation.getGenericLevelId(restTemplate);

        String description = RandomString.make(20);
        ResponseEntity<LesMethodieken> createResponse = GenericCallsEducation.createLesMethodieken(restTemplate,lesId, methodiekId, doelId, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesMethodieken createdLesMethodieken = createResponse.getBody();
        assertThat(createdLesMethodieken).isNotNull();

        // Update the lesMethodieken's description
        String newDescription = RandomString.make(20);
        createdLesMethodieken.setDescription(newDescription);
        HttpEntity<LesMethodieken> requestUpdate = new HttpEntity<>(createdLesMethodieken);
        
        ResponseEntity<LesMethodieken> updateResponse = restTemplate.exchange("/api/lesMethodieken/" + createdLesMethodieken.getLesMethodiekenId().toString(), HttpMethod.PUT, requestUpdate, LesMethodieken.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(newDescription);

        // Verify the lesMethodieken is updated in the database
        ResponseEntity<LesMethodieken> getResponse = restTemplate.getForEntity("/api/lesMethodieken/" + createdLesMethodieken.getLesMethodiekenId().toString(), LesMethodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesMethodieken() {
        // Create a lesMethodieken first
        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        UUID doelId = GenericCallsEducation.getGenericLevelId(restTemplate);

        String description = RandomString.make(20);
        ResponseEntity<LesMethodieken> createResponse = GenericCallsEducation.createLesMethodieken(restTemplate,lesId, methodiekId, doelId, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesMethodieken createdLesMethodieken = createResponse.getBody();
        assertThat(createdLesMethodieken).isNotNull();

        // Delete the lesMethodieken
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesMethodieken/" + createdLesMethodieken.getLesMethodiekenId().toString(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the lesMethodieken is deleted
        ResponseEntity<LesMethodieken> getResponse = restTemplate.getForEntity("/api/lesMethodieken/" + createdLesMethodieken.getLesMethodiekenId().toString(), LesMethodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesMethodiekenById() {
         // Create a lesMethodieken first
         ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
         UUID lesId = les.getBody().getLesId();
 
         ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
         UUID methodiekId = methodiek.getBody().getMethodiekId();
 
         UUID doelId = GenericCallsEducation.getGenericLevelId(restTemplate);
 
         String description = RandomString.make(20);
         ResponseEntity<LesMethodieken> createResponse = GenericCallsEducation.createLesMethodieken(restTemplate,lesId, methodiekId, doelId, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesMethodieken createdLesMethodieken = createResponse.getBody();
        assertThat(createdLesMethodieken).isNotNull();

        // Retrieve the lesMethodieken by ID
        ResponseEntity<LesMethodieken> getResponse = restTemplate.getForEntity("/api/lesMethodieken/" + createdLesMethodieken.getLesMethodiekenId().toString(), LesMethodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLesId()).isEqualTo(lesId);
        assertThat(getResponse.getBody().getMethodiekId()).isEqualTo(methodiekId);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesMethodiekenNotFound() {
        // Try to retrieve a lesMethodieken that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<LesMethodieken> getResponse = restTemplate.getForEntity("/api/lesMethodieken/" + nonExistentId.toString(), LesMethodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLesMethodieken() {
        // Create a lesMethodieken first
        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        UUID doelId = GenericCallsEducation.getGenericLevelId(restTemplate);

        String description = RandomString.make(20);
        ResponseEntity<LesMethodieken> createResponse = GenericCallsEducation.createLesMethodieken(restTemplate,lesId, methodiekId, doelId, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another lesMethodieken with the same ID
        ResponseEntity<LesMethodieken> duplicateResponse = restTemplate.postForEntity("/api/lesMethodieken", createResponse.getBody(), LesMethodieken.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesMethodiekenByIdWithInvalidId() {
        // Attempt to retrieve a lesMethodieken with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LesMethodieken> getResponse = restTemplate.getForEntity("/api/lesMethodieken/" + invalidId, LesMethodieken.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesMethodiekenWithInvalidId() {
        // Attempt to delete a lesMethodieken with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesMethodieken/" + invalidId, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}