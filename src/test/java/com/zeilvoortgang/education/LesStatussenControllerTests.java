package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Les;
import com.zeilvoortgang.education.entity.LesStatussen;
import com.zeilvoortgang.education.entity.Methodieken;
import com.zeilvoortgang.education.entity.ProgressStatussen;
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
import org.springframework.http.HttpEntity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class LesStatussenControllerTests {

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
     void testCreateLesStatussen() {
        // Create a lesStatussen

        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodieken = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodieken.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID methodiekId = methodieken.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<LesStatussen> response = GenericCallsEducation.createLesStatus(restTemplate, lesId, methodiekId,
                userId, progressId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLesId()).isEqualTo(lesId);
        assertThat(response.getBody().getMethodiekId()).isEqualTo(methodiekId);
        assertThat(response.getBody().getUserId()).isEqualTo(userId);
        assertThat(response.getBody().getProgressId()).isEqualTo(progressId);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLesStatussen() {
        // Create a lesStatussen

        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodieken = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodieken.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID methodiekId = methodieken.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<LesStatussen> createResponse = GenericCallsEducation.createLesStatus(restTemplate, lesId,
                methodiekId, userId, progressId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesStatussen createdLesStatussen = createResponse.getBody();
        assertThat(createdLesStatussen).isNotNull();

        // Update the lesStatussen's progressId
        UUID newProgressId = progress.getBody().getProgressStatusId();
        createdLesStatussen.setProgressId(newProgressId);
        HttpEntity<LesStatussen> requestUpdate = new HttpEntity<>(createdLesStatussen);
        ResponseEntity<LesStatussen> updateResponse = restTemplate.exchange(
                "/api/lesStatussen/" + createdLesStatussen.getLesStatusId().toString(), HttpMethod.PUT, requestUpdate,
                LesStatussen.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getProgressId()).isEqualTo(newProgressId);

        // Verify the lesStatussen is updated in the database
        ResponseEntity<LesStatussen> getResponse = restTemplate.getForEntity(
                "/api/lesStatussen/" + createdLesStatussen.getLesStatusId().toString(), LesStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getProgressId()).isEqualTo(newProgressId);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesStatussen() {
        // Create a lesStatussen

        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodieken = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodieken.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID methodiekId = methodieken.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<LesStatussen> createResponse = GenericCallsEducation.createLesStatus(restTemplate, lesId,
                methodiekId, userId, progressId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesStatussen createdLesStatussen = createResponse.getBody();
        assertThat(createdLesStatussen).isNotNull();

        // Delete the lesStatussen
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/lesStatussen/" + createdLesStatussen.getLesStatusId().toString(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the lesStatussen is deleted
        ResponseEntity<LesStatussen> getResponse = restTemplate.getForEntity(
                "/api/lesStatussen/" + createdLesStatussen.getLesStatusId().toString(), LesStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesStatussenById() {
        // Create a lesStatussen

        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodieken = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodieken.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID methodiekId = methodieken.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<LesStatussen> createResponse = GenericCallsEducation.createLesStatus(restTemplate, lesId,
                methodiekId, userId, progressId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LesStatussen createdLesStatussen = createResponse.getBody();
        assertThat(createdLesStatussen).isNotNull();

        // Retrieve the lesStatussen by ID
        ResponseEntity<LesStatussen> getResponse = restTemplate.getForEntity(
                "/api/lesStatussen/" + createdLesStatussen.getLesStatusId().toString(), LesStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getLesId()).isEqualTo(lesId);
        assertThat(getResponse.getBody().getMethodiekId()).isEqualTo(methodiekId);
        assertThat(getResponse.getBody().getUserId()).isEqualTo(userId);
        assertThat(getResponse.getBody().getProgressId()).isEqualTo(progressId);
    }

    @SuppressWarnings("null")
    @Test 
     void testLesStatussenNotFound() {
        // Try to retrieve a lesStatussen that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<LesStatussen> getResponse = restTemplate.getForEntity("/api/lesStatussen/" + nonExistentId,
                LesStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLesStatussen() {
        // Create a lesStatussen

        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodieken = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodieken.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID methodiekId = methodieken.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<LesStatussen> createResponse = GenericCallsEducation.createLesStatus(restTemplate, lesId,
                methodiekId, userId, progressId);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another lesStatussen with the same ID
        ResponseEntity<LesStatussen> duplicateResponse = restTemplate.postForEntity("/api/lesStatussen", createResponse.getBody(),
                LesStatussen.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLesStatussenByIdWithInvalidId() {
        // Attempt to retrieve a lesStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LesStatussen> getResponse = restTemplate.getForEntity("/api/lesStatussen/" + invalidId,
                LesStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLesStatussenWithInvalidId() {
        // Attempt to delete a lesStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/lesStatussen/" + invalidId, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}