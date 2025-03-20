package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.LesTrainers;
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
class LesTrainersControllerTests {

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
     void testCreateLesTrainers() {
        // Create a LesTrainers
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID trainerId = user.getBody().getUserId();

        ResponseEntity<LesTrainers> response = GenericCallsEducation.createLesTrainers(restTemplate, lesId, trainerId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLesId()).isEqualTo(lesId);
        assertThat(response.getBody().getTrainerId()).isEqualTo(trainerId);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesTrainers() {
        // Create a LesTrainers
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID trainerId = user.getBody().getUserId();

        ResponseEntity<LesTrainers> createResponse = GenericCallsEducation.createLesTrainers(restTemplate, lesId, trainerId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesTrainers createdLesTrainers = createResponse.getBody();
        assertThat(createdLesTrainers).isNotNull();

        // Delete the LesTrainers
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/lesTrainers/" + createdLesTrainers.getLesTrainersId().toString(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the LesTrainers is deleted
        ResponseEntity<LesTrainers> getResponse = restTemplate.getForEntity(
                "/api/lesTrainers/" + createdLesTrainers.getLesTrainersId().toString(), LesTrainers.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesTrainersById() {
        // Create a LesTrainers
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID trainerId = user.getBody().getUserId();

        ResponseEntity<LesTrainers> createResponse = GenericCallsEducation.createLesTrainers(restTemplate, lesId, trainerId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesTrainers createdLesTrainers = createResponse.getBody();
        assertThat(createdLesTrainers).isNotNull();

        // Retrieve the LesTrainers by ID
        ResponseEntity<LesTrainers> getResponse = restTemplate.getForEntity(
                "/api/lesTrainers/" + createdLesTrainers.getLesTrainersId().toString(), LesTrainers.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLesId()).isEqualTo(lesId);
        assertThat(getResponse.getBody().getTrainerId()).isEqualTo(trainerId);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesTrainersNotFound() {
        // Try to retrieve a LesTrainers that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<LesTrainers> getResponse = restTemplate.getForEntity(
                "/api/lesTrainers/" + nonExistentId.toString(),
                LesTrainers.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLesTrainers() {
        // Create a LesTrainers
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID trainerId = user.getBody().getUserId();

        ResponseEntity<LesTrainers> createResponse = GenericCallsEducation.createLesTrainers(restTemplate, lesId, trainerId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another LesTrainers with the same ID
        ResponseEntity<LesTrainers> duplicateResponse = restTemplate.postForEntity("/api/lesTrainers",
                createResponse.getBody(),
                LesTrainers.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesTrainersByIdWithInvalidId() {
        // Attempt to retrieve a LesTrainers with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LesTrainers> getResponse = restTemplate.getForEntity("/api/lesTrainers/" + invalidId.toString(),
                LesTrainers.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesTrainersWithInvalidId() {
        // Attempt to delete a LesTrainers with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesTrainers/" + invalidId, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
