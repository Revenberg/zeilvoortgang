package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Authorizations;

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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangAuthorizationsApplicationTests {

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
    void testCreateAuthorization() {
        String description = RandomString.make(20);
        String page = RandomString.make(15);
        String raci = "Responsible";
        ResponseEntity<Authorizations> response = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isEqualTo(description);
        assertThat(response.getBody().getPage()).isEqualTo(page);
        assertThat(response.getBody().getRaci()).isEqualTo(raci);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorization() {
        // Create an authorization first
        String description = RandomString.make(20);
        String page = RandomString.make(15);
        String raci = "Responsible";
        ResponseEntity<Authorizations> createResponse = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Authorizations createdAuthorization = createResponse.getBody();
        assertThat(createdAuthorization).isNotNull();

        // Update the description
        String newDescription = RandomString.make(25);
        createdAuthorization.setDescription(newDescription);
        HttpEntity<Authorizations> requestUpdate = new HttpEntity<>(createdAuthorization);
        ResponseEntity<Authorizations> updateResponse = restTemplate.exchange("/api/authorizations/" + createdAuthorization.getAuthorizationId(), HttpMethod.PUT, requestUpdate, Authorizations.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorization() {
        // Create an authorization first
        String description = RandomString.make(20);
        String page = RandomString.make(15);
        String raci = "Responsible";
        ResponseEntity<Authorizations> createResponse = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Authorizations createdAuthorization = createResponse.getBody();
        assertThat(createdAuthorization).isNotNull();

        // Delete the authorization
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorizations/" + createdAuthorization.getAuthorizationId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the authorization is deleted
        ResponseEntity<Authorizations> getResponse = restTemplate.getForEntity("/api/authorizations/" + createdAuthorization.getAuthorizationId(), Authorizations.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationById() {
        // Create an authorization first
        String description = RandomString.make(20);
        String page = RandomString.make(15);
        String raci = "Responsible";
        ResponseEntity<Authorizations> createResponse = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Authorizations createdAuthorization = createResponse.getBody();
        assertThat(createdAuthorization).isNotNull();

        // Retrieve the authorization by ID
        ResponseEntity<Authorizations> getResponse = restTemplate.getForEntity("/api/authorizations/" + createdAuthorization.getAuthorizationId(), Authorizations.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(description);
        assertThat(getResponse.getBody().getPage()).isEqualTo(page);
        assertThat(getResponse.getBody().getRaci()).isEqualTo(raci);
    }

    @SuppressWarnings("null")
    @Test
    void testAuthorizationNotFound() {
        // Try to retrieve an authorization that does not exist
        UUID nonExistentAuthorizationId = UUID.randomUUID();
        ResponseEntity<Authorizations> getResponse = restTemplate.getForEntity("/api/authorizations/" + nonExistentAuthorizationId, Authorizations.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateDuplicateAuthorization() {
        // Create an authorization first
        String description = RandomString.make(20);
        String page = RandomString.make(15);
        String raci = "Responsible";
        ResponseEntity<Authorizations> createResponse = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another authorization with the same description and page
        ResponseEntity<Authorizations> duplicateResponse = GenericCallsUsermanagement.createAuthorization(restTemplate, description, page, raci);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationByIdWithInvalidUUID() {
        // Attempt to retrieve an authorization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Authorizations> getResponse = restTemplate.getForEntity("/api/authorizations/" + invalidUUID, Authorizations.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorizationWithInvalidUUID() {
        // Attempt to delete an authorization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorizations/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorizationWithInvalidUUID() {
        // Attempt to update an authorization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        Authorizations authorization = new Authorizations();
        authorization.setDescription(RandomString.make(20));
        authorization.setPage(RandomString.make(15));
        authorization.setRaci("Responsible");
        HttpEntity<Authorizations> requestUpdate = new HttpEntity<>(authorization);
        ResponseEntity<Authorizations> updateResponse = restTemplate.exchange("/api/authorizations/" + invalidUUID, HttpMethod.PUT, requestUpdate, Authorizations.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}