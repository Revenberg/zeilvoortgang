package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.AuthorizationGroups;
import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;
import com.zeilvoortgang.usermanagement.entity.Authorizations;

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
class ZeilvoortgangAuthorizationGroupsApplicationTests {

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
    void testCreateAuthorizationGroup() {
        // Create an authorization group first
        ResponseEntity<Authorizations> authorization = GenericCallsUsermanagement.createAuthorization(restTemplate);
        assertThat(authorization.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorization.getBody()).isNotNull();
        UUID authorizationId = authorization.getBody().getAuthorizationId();

        ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
        assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorizationRole.getBody()).isNotNull();
        UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();

        ResponseEntity<AuthorizationGroups> response = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getAuthorizationId()).isEqualTo(authorizationId);
        assertThat(response.getBody().getAuthorizationRoleId()).isEqualTo(authorizationRoleId);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorizationGroup() {
                // Create an authorization group first
                ResponseEntity<Authorizations> authorization = GenericCallsUsermanagement.createAuthorization(restTemplate);
                assertThat(authorization.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(authorization.getBody()).isNotNull();
                UUID authorizationId = authorization.getBody().getAuthorizationId();
        
                ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
                assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(authorizationRole.getBody()).isNotNull();
                UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();
        
                ResponseEntity<AuthorizationGroups> createResponse = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationGroups createdGroup = createResponse.getBody();
        assertThat(createdGroup).isNotNull();

        // Update the authorization role ID
        ResponseEntity<Authorizations> authorizationNew = GenericCallsUsermanagement.createAuthorization(restTemplate);
        assertThat(authorizationNew.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorizationNew.getBody()).isNotNull();
        UUID authorizationIdNew = authorizationNew.getBody().getAuthorizationId();

        createdGroup.setAuthorizationId(authorizationIdNew);
        HttpEntity<AuthorizationGroups> requestUpdate = new HttpEntity<>(createdGroup);
        ResponseEntity<AuthorizationGroups> updateResponse = restTemplate.exchange("/api/authorizationGroups/" + createdGroup.getAuthorizationGroupsId(), HttpMethod.PUT, requestUpdate, AuthorizationGroups.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getAuthorizationId()).isEqualTo(authorizationIdNew);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorizationGroup() {
        // Create an authorization group first                                          
                                ResponseEntity<AuthorizationGroups> createResponse = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationGroups createdGroup = createResponse.getBody();
        assertThat(createdGroup).isNotNull();

        // Delete the authorization group
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorizationGroups/" + createdGroup.getAuthorizationGroupsId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the authorization group is deleted
        ResponseEntity<AuthorizationGroups> getResponse = restTemplate.getForEntity("/api/authorizationGroups/" + createdGroup.getAuthorizationGroupsId(), AuthorizationGroups.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationGroupById() {
                        // Create an authorization group first
                        ResponseEntity<Authorizations> authorization = GenericCallsUsermanagement.createAuthorization(restTemplate);
                        assertThat(authorization.getStatusCode()).isEqualTo(HttpStatus.OK);
                        assertThat(authorization.getBody()).isNotNull();
                        UUID authorizationId = authorization.getBody().getAuthorizationId();
                
                        ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
                        assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
                        assertThat(authorizationRole.getBody()).isNotNull();
                        UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();
                
                        ResponseEntity<AuthorizationGroups> createResponse = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationGroups createdGroup = createResponse.getBody();
        assertThat(createdGroup).isNotNull();

        // Retrieve the authorization group by ID
        ResponseEntity<AuthorizationGroups> getResponse = restTemplate.getForEntity("/api/authorizationGroups/" + createdGroup.getAuthorizationGroupsId(), AuthorizationGroups.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getAuthorizationId()).isEqualTo(authorizationId);
        assertThat(getResponse.getBody().getAuthorizationRoleId()).isEqualTo(authorizationRoleId);
    }

    @SuppressWarnings("null")
    @Test
    void testAuthorizationGroupNotFound() {
        // Try to retrieve an authorization group that does not exist
        UUID nonExistentGroupId = UUID.randomUUID();
        ResponseEntity<AuthorizationGroups> getResponse = restTemplate.getForEntity("/api/authorizationGroups/" + nonExistentGroupId, AuthorizationGroups.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateDuplicateAuthorizationGroup() {
        // Create an authorization group first
                        // Create an authorization group first
                        ResponseEntity<Authorizations> authorization = GenericCallsUsermanagement.createAuthorization(restTemplate);
                        assertThat(authorization.getStatusCode()).isEqualTo(HttpStatus.OK);
                        assertThat(authorization.getBody()).isNotNull();
                        UUID authorizationId = authorization.getBody().getAuthorizationId();
                
                        ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
                        assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
                        assertThat(authorizationRole.getBody()).isNotNull();
                        UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();
                
                        ResponseEntity<AuthorizationGroups> createResponse = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another authorization group with the same authorizationId and authorizationRoleId
        ResponseEntity<AuthorizationGroups> duplicateResponse = GenericCallsUsermanagement.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationGroupByIdWithInvalidUUID() {
        // Attempt to retrieve an authorization group with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<AuthorizationGroups> getResponse = restTemplate.getForEntity("/api/authorizationGroups/" + invalidUUID, AuthorizationGroups.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorizationGroupWithInvalidUUID() {
        // Attempt to delete an authorization group with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorizationGroups/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorizationGroupWithInvalidUUID() {
        // Attempt to update an authorization group with an invalid UUID
        String invalidUUID = "invalid-uuid";
        AuthorizationGroups group = new AuthorizationGroups();
        group.setAuthorizationId(UUID.randomUUID());
        group.setAuthorizationRoleId(UUID.randomUUID());
        HttpEntity<AuthorizationGroups> requestUpdate = new HttpEntity<>(group);
        ResponseEntity<AuthorizationGroups> updateResponse = restTemplate.exchange("/api/authorizationGroups/" + invalidUUID, HttpMethod.PUT, requestUpdate, AuthorizationGroups.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}