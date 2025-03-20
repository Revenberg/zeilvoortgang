package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;

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
class ZeilvoortgangAuthorizationRolesApplicationTests {

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
    void testCreateAuthorizationRole() {
        String roleName = RandomString.make(20);
        ResponseEntity<AuthorizationRoles> response = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getRoleName()).isEqualTo(roleName);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorizationRole() {
        // Create an authorization role first
        String roleName = RandomString.make(20);
        ResponseEntity<AuthorizationRoles> createResponse = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationRoles createdRole = createResponse.getBody();
        assertThat(createdRole).isNotNull();

        // Update the role name
        String newRoleName = RandomString.make(25);
        createdRole.setRoleName(newRoleName);
        HttpEntity<AuthorizationRoles> requestUpdate = new HttpEntity<>(createdRole);
        ResponseEntity<AuthorizationRoles> updateResponse = restTemplate.exchange("/api/authorization-roles/" + createdRole.getAuthorizationRoleId(), HttpMethod.PUT, requestUpdate, AuthorizationRoles.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getRoleName()).isEqualTo(newRoleName);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorizationRole() {
        // Create an authorization role first
        String roleName = RandomString.make(20);
        ResponseEntity<AuthorizationRoles> createResponse = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationRoles createdRole = createResponse.getBody();
        assertThat(createdRole).isNotNull();

        // Delete the authorization role
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorization-roles/" + createdRole.getAuthorizationRoleId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the authorization role is deleted
        ResponseEntity<AuthorizationRoles> getResponse = restTemplate.getForEntity("/api/authorization-roles/" + createdRole.getAuthorizationRoleId(), AuthorizationRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationRoleById() {
        // Create an authorization role first
        String roleName = RandomString.make(20);
        ResponseEntity<AuthorizationRoles> createResponse = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        AuthorizationRoles createdRole = createResponse.getBody();
        assertThat(createdRole).isNotNull();

        // Retrieve the authorization role by ID
        ResponseEntity<AuthorizationRoles> getResponse = restTemplate.getForEntity("/api/authorization-roles/" + createdRole.getAuthorizationRoleId(), AuthorizationRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getRoleName()).isEqualTo(roleName);
    }

    @SuppressWarnings("null")
    @Test
    void testAuthorizationRoleNotFound() {
        // Try to retrieve an authorization role that does not exist
        UUID nonExistentRoleId = UUID.randomUUID();
        ResponseEntity<AuthorizationRoles> getResponse = restTemplate.getForEntity("/api/authorization-roles/" + nonExistentRoleId, AuthorizationRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test
    void testCreateDuplicateAuthorizationRole() {
        // Create an authorization role first
        String roleName = RandomString.make(20);
        ResponseEntity<AuthorizationRoles> createResponse = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another authorization role with the same role name
        ResponseEntity<AuthorizationRoles> duplicateResponse = GenericCallsUsermanagement.createAuthorizationRole(restTemplate, roleName);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test
    void testGetAuthorizationRoleByIdWithInvalidUUID() {
        // Attempt to retrieve an authorization role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<AuthorizationRoles> getResponse = restTemplate.getForEntity("/api/authorization-roles/" + invalidUUID, AuthorizationRoles.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testDeleteAuthorizationRoleWithInvalidUUID() {
        // Attempt to delete an authorization role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/authorization-roles/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdateAuthorizationRoleWithInvalidUUID() {
        // Attempt to update an authorization role with an invalid UUID
        String invalidUUID = "invalid-uuid";
        AuthorizationRoles role = new AuthorizationRoles();
        role.setRoleName(RandomString.make(20));
        HttpEntity<AuthorizationRoles> requestUpdate = new HttpEntity<>(role);
        ResponseEntity<AuthorizationRoles> updateResponse = restTemplate.exchange("/api/authorization-roles/" + invalidUUID, HttpMethod.PUT, requestUpdate, AuthorizationRoles.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}