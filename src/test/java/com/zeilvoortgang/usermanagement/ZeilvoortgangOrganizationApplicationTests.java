package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Organization;

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
class ZeilvoortgangOrganizationApplicationTests {

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
     void testCreateOrganization() {
        String name = RandomString.make(20);
        ResponseEntity<Organization> response = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateOrganization() {
        // Create an organization first
        String name = RandomString.make(20);
        ResponseEntity<Organization> createResponse = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Organization createdOrganization = createResponse.getBody();
        String newName = RandomString.make(20);
        createdOrganization.setName(newName);
        assertThat(createdOrganization).isNotNull();

        HttpEntity<Organization> requestUpdate = new HttpEntity<>(createdOrganization);
        ResponseEntity<Organization> updateResponse = restTemplate.exchange("/api/organizations/" + createdOrganization.getOrganizationId(), HttpMethod.PUT, requestUpdate, Organization.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();

        // Verify the organization is updated in the database
        ResponseEntity<Organization> getResponse = restTemplate.getForEntity("/api/organizations/" + createdOrganization.getOrganizationId(), Organization.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo(newName);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteOrganization() {
        // Create an organization first
        String name = RandomString.make(20);
        ResponseEntity<Organization> createResponse = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Organization createdOrganization = createResponse.getBody();
        assertThat(createdOrganization).isNotNull();

        // Delete the organization
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/organizations/" + createdOrganization.getOrganizationId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the organization is deleted
        ResponseEntity<Organization> getResponse = restTemplate.getForEntity("/api/organizations/" + createdOrganization.getOrganizationId(), Organization.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetOrganizationById() {
        // Create an organization first
        String name = RandomString.make(20);
        ResponseEntity<Organization> createResponse = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Organization createdOrganization = createResponse.getBody();
        assertThat(createdOrganization).isNotNull();

        // Retrieve the organization by ID
        ResponseEntity<Organization> getResponse = restTemplate.getForEntity("/api/organizations/" + createdOrganization.getOrganizationId(), Organization.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo(name);
    }

    @SuppressWarnings("null")
    @Test 
     void testOrganizationNotFound() {
        // Try to retrieve an organization that does not exist
        UUID nonExistentOrganizationId = UUID.randomUUID();
        ResponseEntity<Organization> getResponse = restTemplate.getForEntity("/api/organizations/" + nonExistentOrganizationId, Organization.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateOrganizationWithSameName() {
        // Create an organization first
        String name = RandomString.make(20);
        ResponseEntity<Organization> createResponse = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another organization with the same name
        ResponseEntity<Organization> duplicateResponse = GenericCallsUsermanagement.createOrganization(restTemplate, name);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateOrganizationWithSameName() {
        // Create two organizations
        String name1 = RandomString.make(20);
        ResponseEntity<Organization> createResponse1 = GenericCallsUsermanagement.createOrganization(restTemplate, name1);
        assertThat(createResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        Organization organization1 = createResponse1.getBody();
        assertThat(organization1).isNotNull();

        String name2 = RandomString.make(20);
        ResponseEntity<Organization> createResponse2 = GenericCallsUsermanagement.createOrganization(restTemplate, name2);
        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        Organization organization2 = createResponse2.getBody();
        assertThat(organization2).isNotNull();

        // Try to update organization2 with the same name as organization1
        organization2.setName(name1);
        HttpEntity<Organization> requestUpdate = new HttpEntity<>(organization2);
        ResponseEntity<Organization> updateResponse = restTemplate.exchange("/api/organizations/" + organization2.getOrganizationId(), HttpMethod.PUT, requestUpdate, Organization.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetOrganizationByIdWithInvalidUUID() {
        // Attempt to retrieve an organization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Organization> getResponse = restTemplate.getForEntity("/api/organizations/" + invalidUUID, Organization.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteOrganizationWithInvalidUUID() {
        // Attempt to delete an organization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/organizations/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateOrganizationWithInvalidUUID() {
        // Attempt to update an organization with an invalid UUID
        String invalidUUID = "invalid-uuid";
        Organization organization = new Organization();
        organization.setName(RandomString.make(20));
        HttpEntity<Organization> requestUpdate = new HttpEntity<>(organization);
        ResponseEntity<Organization> updateResponse = restTemplate.exchange("/api/organizations/" + invalidUUID, HttpMethod.PUT, requestUpdate, Organization.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}