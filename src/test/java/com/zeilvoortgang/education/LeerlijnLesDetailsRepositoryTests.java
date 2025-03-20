package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.LeerlijnLes;
import com.zeilvoortgang.education.entity.LeerlijnLesDetails;
import com.zeilvoortgang.education.entity.Methodieken;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import org.springframework.http.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class LeerlijnLesDetailsRepositoryTests {

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

        @SuppressWarnings({ "null", "unused" })
        @Test
        void testSaveLeerlijnLesDetails() {
                // Create a new leerlijnLesDetails
                ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation
                                .createProgressStatussen(restTemplate);
                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(createResponse.getBody()).isNotNull();
                UUID progressId = createResponse.getBody().getProgressStatusId();

                ResponseEntity<LeerlijnLes> leerlijnLes = GenericCallsEducation.createLeerlijnLes(restTemplate);
                assertThat(leerlijnLes.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(leerlijnLes.getBody()).isNotNull();
                UUID leerlijnLesId = leerlijnLes.getBody().getLeerlijnLesId();

                ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
                assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(methodiek.getBody()).isNotNull();
                UUID methodiekId = methodiek.getBody().getMethodiekId();

                ResponseEntity<LeerlijnLesDetails> response = GenericCallsEducation.createLeerlijnLesDetails(
                                restTemplate,
                                leerlijnLesId, methodiekId, progressId);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                LeerlijnLesDetails createdLeerlijnLesDetails = response.getBody();

                assertThat(response.getBody().getLeerlijnLesId()).isEqualTo(leerlijnLesId);
                assertThat(response.getBody().getMethodiekId()).isEqualTo(methodiekId);
                assertThat(response.getBody().getProgressStatusId()).isEqualTo(progressId);
        }

        @SuppressWarnings("null")
        @Test
        void testFindLeerlijnLesDetailsById() {
                // Create a new leerlijnLesDetails
                ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation
                                .createProgressStatussen(restTemplate);
                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(createResponse.getBody()).isNotNull();
                UUID progressId = createResponse.getBody().getProgressStatusId();

                ResponseEntity<LeerlijnLes> leerlijnLes = GenericCallsEducation.createLeerlijnLes(restTemplate);
                assertThat(leerlijnLes.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(leerlijnLes.getBody()).isNotNull();
                UUID leerlijnLesId = leerlijnLes.getBody().getLeerlijnLesId();

                ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
                assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(methodiek.getBody()).isNotNull();
                UUID methodiekId = methodiek.getBody().getMethodiekId();

                ResponseEntity<LeerlijnLesDetails> response = GenericCallsEducation.createLeerlijnLesDetails(
                                restTemplate,
                                leerlijnLesId, methodiekId, progressId);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                assertThat(response.getBody().getLeerlijnLesId()).isEqualTo(leerlijnLesId);
                assertThat(response.getBody().getMethodiekId()).isEqualTo(methodiekId);
                assertThat(response.getBody().getProgressStatusId()).isEqualTo(progressId);
        }

        @SuppressWarnings({ "null", "unused" })
        @Test
        void testUpdateLeerlijnLesDetails() {
                // Create a new leerlijnLesDetails
                ResponseEntity<LeerlijnLes> leerlijnLes = GenericCallsEducation.createLeerlijnLes(restTemplate);

                ResponseEntity<ProgressStatussen> progressStatus = GenericCallsEducation
                                .createProgressStatussen(restTemplate);
                assertThat(progressStatus.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(progressStatus.getBody()).isNotNull();
                UUID progressId = progressStatus.getBody().getProgressStatusId();

                assertThat(leerlijnLes.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(leerlijnLes.getBody()).isNotNull();
                UUID leerlijnLesId = leerlijnLes.getBody().getLeerlijnLesId();

                ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
                assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(methodiek.getBody()).isNotNull();
                UUID methodiekId = methodiek.getBody().getMethodiekId();

                ResponseEntity<LeerlijnLesDetails> createResponse = GenericCallsEducation.createLeerlijnLesDetails(
                                restTemplate,
                                leerlijnLesId, methodiekId, progressId);

                // Create a new leerlijnLesDetails
                ResponseEntity<ProgressStatussen> newProgress = GenericCallsEducation
                                .createProgressStatussen(restTemplate);
                assertThat(newProgress.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(newProgress.getBody()).isNotNull();
                UUID progressIdNew = newProgress.getBody().getProgressStatusId();

                LeerlijnLesDetails createdLeerlijnLesDetails = createResponse.getBody();
                createdLeerlijnLesDetails.setProgressStatusId(progressIdNew);

                HttpEntity<LeerlijnLesDetails> requestUpdate = new HttpEntity<>(createdLeerlijnLesDetails);
                ResponseEntity<LeerlijnLesDetails> updateResponse = restTemplate.exchange(
                                "/api/leerlijnLesDetails/"
                                                + createdLeerlijnLesDetails.getLeerlijnLesDetailsId().toString(),
                                HttpMethod.PUT, requestUpdate, LeerlijnLesDetails.class);

                assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(updateResponse.getBody()).isNotNull();
                LeerlijnLesDetails updatedLeerlijnLesDetails = updateResponse.getBody();
                assertThat(updateResponse.getBody().getLeerlijnLesId()).isEqualTo(leerlijnLesId);
                assertThat(updateResponse.getBody().getMethodiekId()).isEqualTo(methodiekId);
                assertThat(updateResponse.getBody().getProgressStatusId()).isEqualTo(progressIdNew);
        }

        @SuppressWarnings("null")
        @Test
        void testDeleteLeerlijnLesDetailsById() {
                ResponseEntity<LeerlijnLesDetails> createResponse = GenericCallsEducation
                                .createLeerlijnLesDetails(restTemplate);

                restTemplate.delete("/api/leerlijnLesDetails/"
                                + createResponse.getBody().getLeerlijnLesDetailsId().toString());

                ResponseEntity<LeerlijnLesDetails> response = restTemplate.getForEntity(
                                "/api/leerlijnLesDetails/"
                                                + createResponse.getBody().getLeerlijnLesDetailsId().toString(),
                                LeerlijnLesDetails.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @SuppressWarnings("null")
        @Test
        void testCreateDuplicateLeerlijnLesDetails() {
                // Create a new leerlijnLesDetails
                ResponseEntity<LeerlijnLes> leerlijnLes = GenericCallsEducation.createLeerlijnLes(restTemplate);

                ResponseEntity<ProgressStatussen> progressStatus = GenericCallsEducation
                                .createProgressStatussen(restTemplate);
                assertThat(progressStatus.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(progressStatus.getBody()).isNotNull();
                UUID progressId = progressStatus.getBody().getProgressStatusId();

                assertThat(leerlijnLes.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(leerlijnLes.getBody()).isNotNull();
                UUID leerlijnLesId = leerlijnLes.getBody().getLeerlijnLesId();

                ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
                assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(methodiek.getBody()).isNotNull();
                UUID methodiekId = methodiek.getBody().getMethodiekId();

                ResponseEntity<LeerlijnLesDetails> createResponse = GenericCallsEducation
                                .createLeerlijnLesDetails(restTemplate, leerlijnLesId, methodiekId, progressId);

                assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

                // Attempt to create a duplicate leerlijnLesDetails
                ResponseEntity<LeerlijnLesDetails> duplicateResponse = GenericCallsEducation
                                .createLeerlijnLesDetails(restTemplate, leerlijnLesId, methodiekId, progressId);
                assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        }

        @SuppressWarnings("null")
        @Test
        void testGetLeerlijnLesDetailsByInvalidId() {
                // Attempt to retrieve a leerlijnLesDetails with an invalid ID
                String invalidId = RandomString.make(20);
                ResponseEntity<LeerlijnLesDetails> response = restTemplate.getForEntity(
                                "/api/leerlijnLesDetails/" + invalidId,
                                LeerlijnLesDetails.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @SuppressWarnings("null")
        @Test
        void testDeleteLeerlijnLesDetailsWithInvalidId() {
                // Attempt to delete a leerlijnLesDetails with an invalid ID
                String invalidId = RandomString.make(20);
                ResponseEntity<Void> response = restTemplate.exchange("/api/leerlijnLesDetails/" + invalidId,
                                HttpMethod.DELETE,
                                null, Void.class);
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
}