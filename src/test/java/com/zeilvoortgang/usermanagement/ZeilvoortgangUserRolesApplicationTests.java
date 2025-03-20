package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;
import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.usermanagement.entity.UserRoles;

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
class ZeilvoortgangUserRolesApplicationTests {

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
                        .rootUri("http://localhost:" + port));
    }

    @SuppressWarnings("null")
    @Test
    void testCreateUserRole() {
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        assertThat(user.getBody().getUserId()).isNotNull();
        UUID userId = user.getBody().getUserId();

        ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
        assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorizationRole.getBody()).isNotNull();
        assertThat(authorizationRole.getBody().getAuthorizationRoleId()).isNotNull();
        UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();

        ResponseEntity<UserRoles> response = GenericCallsUsermanagement.createUserRole(restTemplate, userId, authorizationRoleId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(userId);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateUserRole() {
        // Create a user role first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        assertThat(user.getBody().getUserId()).isNotNull();
        UUID userId = user.getBody().getUserId();

        ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
        assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorizationRole.getBody()).isNotNull();
        assertThat(authorizationRole.getBody().getAuthorizationRoleId()).isNotNull();
        UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();

        ResponseEntity<UserRoles> createResponse = GenericCallsUsermanagement.createUserRole(restTemplate, userId, authorizationRoleId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserRoles createdUserRole = createResponse.getBody();
        assertThat(createdUserRole).isNotNull();

        // Update the user in role
        ResponseEntity<User> userNew = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(userNew.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userNew.getBody()).isNotNull();
        assertThat(userNew.getBody().getUserId()).isNotNull();
        UUID userIdNew = userNew.getBody().getUserId();

        createdUserRole.setUserId(userIdNew);
        HttpEntity<UserRoles> requestUpdate = new HttpEntity<>(createdUserRole);
        ResponseEntity<UserRoles> updateResponse = restTemplate.exchange(
                "/api/user-roles/" + createdUserRole.getUserRolesId(), HttpMethod.PUT, requestUpdate, UserRoles.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getUserId()).isEqualTo(userIdNew);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteUserRole() {
        // Create a user role first
        ResponseEntity<UserRoles> createResponse = GenericCallsUsermanagement.createUserRole(restTemplate);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserRoles createdUserRole = createResponse.getBody();
        assertThat(createdUserRole).isNotNull();

        // Delete the user role
        ResponseEntity<Void> deleteResponse = restTemplate
                .exchange("/api/user-roles/" + createdUserRole.getUserRolesId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the user role is deleted
        ResponseEntity<UserRoles> getResponse = restTemplate
                .getForEntity("/api/user-roles/" + createdUserRole.getUserRolesId(), UserRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testGetUserRoleById() {
        // Create a user role first
                ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
                assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(user.getBody()).isNotNull();
                assertThat(user.getBody().getUserId()).isNotNull();
                UUID userId = user.getBody().getUserId();
        
                ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
                assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(authorizationRole.getBody()).isNotNull();
                assertThat(authorizationRole.getBody().getAuthorizationRoleId()).isNotNull();
                UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();
        
                ResponseEntity<UserRoles> createResponse = GenericCallsUsermanagement.createUserRole(restTemplate, userId, authorizationRoleId);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserRoles createdUserRole = createResponse.getBody();
        assertThat(createdUserRole).isNotNull();

        // Retrieve the user role by ID
        ResponseEntity<UserRoles> getResponse = restTemplate
                .getForEntity("/api/user-roles/" + createdUserRole.getUserRolesId(), UserRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getUserId()).isEqualTo(userId);
        assertThat(getResponse.getBody().getAuthorizationRoleId()).isEqualTo(authorizationRoleId);
    }

    @SuppressWarnings("null")
    @Test
    void testUserRoleNotFound() {
        // Try to retrieve a user role that does not exist
        UUID nonExistentUserRoleId = UUID.randomUUID();
        ResponseEntity<UserRoles> getResponse = restTemplate.getForEntity("/api/user-roles/" + nonExistentUserRoleId,
                UserRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateDuplicateUserRole() {
// Create a user role first
ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(user.getBody()).isNotNull();
assertThat(user.getBody().getUserId()).isNotNull();
UUID userId = user.getBody().getUserId();

ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement.createAuthorizationRole(restTemplate);
assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
assertThat(authorizationRole.getBody()).isNotNull();
assertThat(authorizationRole.getBody().getAuthorizationRoleId()).isNotNull();
UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();

ResponseEntity<UserRoles> createResponse = GenericCallsUsermanagement.createUserRole(restTemplate, userId, authorizationRoleId);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another user role with the same userId and authorizationRoleId
        ResponseEntity<UserRoles> duplicateResponse = GenericCallsUsermanagement.createUserRole(restTemplate, userId, authorizationRoleId);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test
    void testGetUserRoleByIdWithInvalidUUID() {
        // Attempt to retrieve a user role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<UserRoles> getResponse = restTemplate.getForEntity("/api/user-roles/" + invalidUUID,
                UserRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteUserRoleWithInvalidUUID() {
        // Attempt to delete a user role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/user-roles/" + invalidUUID, HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateUserRoleWithInvalidUUID() {
        // Attempt to update a user role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        UserRoles userRole = new UserRoles();
        userRole.setUserId(UUID.randomUUID());
        userRole.setAuthorizationRoleId(UUID.randomUUID());
        HttpEntity<UserRoles> requestUpdate = new HttpEntity<>(userRole);
        ResponseEntity<UserRoles> updateResponse = restTemplate.exchange("/api/user-roles/" + invalidUUID,
                HttpMethod.PUT, requestUpdate, UserRoles.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}