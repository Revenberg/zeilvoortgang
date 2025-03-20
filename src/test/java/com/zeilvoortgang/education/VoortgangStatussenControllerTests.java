package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Les;
import com.zeilvoortgang.education.entity.Methodieken;
import com.zeilvoortgang.education.entity.ProgressStatussen;
import com.zeilvoortgang.education.entity.VoortgangStatussen;
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
class VoortgangStatussenControllerTests {

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
     void testCreateVoortgangStatussen() {
        // Create a voortgangStatussen
        ResponseEntity<Les> les = GenericCallsEducation.createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(les.getBody()).isNotNull();
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(methodiek.getBody()).isNotNull();
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(progress.getBody()).isNotNull();
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<VoortgangStatussen> response = GenericCallsEducation.createVoortgangStatussen(restTemplate,
                lesId, methodiekId, userId, progressId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getLesId()).isEqualTo(lesId);
        assertThat(response.getBody().getMethodiekId()).isEqualTo(methodiekId);
        assertThat(response.getBody().getUserId()).isEqualTo(userId);
        assertThat(response.getBody().getProgressId()).isEqualTo(progressId);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateVoortgangStatussen() {
        // Create a voortgangStatussen
        ResponseEntity<VoortgangStatussen> createResponse = GenericCallsEducation
                .createVoortgangStatussen(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        VoortgangStatussen createdVoortgangStatussen = createResponse.getBody();
        assertThat(createdVoortgangStatussen).isNotNull();

        createdVoortgangStatussen.setLastUpdateIdentifier("updated-system");

        HttpEntity<VoortgangStatussen> requestUpdate = new HttpEntity<>(createdVoortgangStatussen);
        ResponseEntity<VoortgangStatussen> updateResponse = restTemplate.exchange(
                "/api/voortgangStatussen/" + createdVoortgangStatussen.getVoortgangStatussenId(),
                HttpMethod.PUT,
                requestUpdate,
                VoortgangStatussen.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getLastUpdateIdentifier()).isEqualTo("updated-system");
    }

    @Test 
     void testDeleteVoortgangStatussen() {
        ResponseEntity<VoortgangStatussen> voortgangStatussen = GenericCallsEducation
                .createVoortgangStatussen(restTemplate);
        assertThat(voortgangStatussen.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(voortgangStatussen.getBody()).isNotNull();
        @SuppressWarnings("null")
        
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/voortgangStatussen/" + voortgangStatussen.getBody().getVoortgangStatussenId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        @SuppressWarnings("null")
        ResponseEntity<VoortgangStatussen> getResponse = restTemplate.getForEntity(
                "/api/voortgangStatussen/" + voortgangStatussen.getBody().getVoortgangStatussenId(),
                VoortgangStatussen.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetVoortgangStatussenById() {
        ResponseEntity<VoortgangStatussen> createResponse = GenericCallsEducation
                .createVoortgangStatussen(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        
        VoortgangStatussen createdVoortgangStatussen = createResponse.getBody();
        assertThat(createdVoortgangStatussen).isNotNull();

        ResponseEntity<VoortgangStatussen> getResponse = restTemplate.getForEntity(
                "/api/voortgangStatussen/" + createdVoortgangStatussen.getVoortgangStatussenId(),
                VoortgangStatussen.class);

        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getVoortgangStatussenId())
                .isEqualTo(createResponse.getBody().getVoortgangStatussenId());
    }

    @SuppressWarnings("null")
    @Test 
     void testVoortgangStatussenNotFound() {
        // Try to retrieve a voortgangStatussen that does not exist
        UUID nonExistentId = UUID.randomUUID();
        ResponseEntity<VoortgangStatussen> getResponse = restTemplate
                .getForEntity("/api/voortgangStatussen/" + nonExistentId, VoortgangStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateVoortgangStatussen() {
        // Create a voortgangStatussen first
        ResponseEntity<VoortgangStatussen> voortgangStatussen = GenericCallsEducation
                .createVoortgangStatussen(restTemplate);
        assertThat(voortgangStatussen.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(voortgangStatussen.getBody()).isNotNull();

        // Try to create another voortgangStatussen with the same information
        ResponseEntity<VoortgangStatussen> duplicateResponse = restTemplate.postForEntity("/api/voortgangStatussen",
                voortgangStatussen.getBody(), VoortgangStatussen.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetVoortgangStatussenByIdWithInvalidId() {
        // Attempt to retrieve a voortgangStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<VoortgangStatussen> getResponse = restTemplate
                .getForEntity("/api/voortgangStatussen/" + invalidId, VoortgangStatussen.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteVoortgangStatussenWithInvalidId() {
        // Attempt to delete a voortgangStatussen with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/voortgangStatussen/" + invalidId,
                HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}