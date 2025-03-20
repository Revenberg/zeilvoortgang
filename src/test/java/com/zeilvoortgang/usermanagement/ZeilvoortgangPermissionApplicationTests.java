package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Permission;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangPermissionApplicationTests {

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
     void testCreatePermission() {
        String permissionName = RandomString.make(20);
        ResponseEntity<Permission> response = GenericCallsUsermanagement.createPermission(restTemplate, permissionName);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(permissionName);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdatePermission() {
        // Create a permission first
        String permissionName = RandomString.make(20);
        ResponseEntity<Permission> createResponse = GenericCallsUsermanagement.createPermission(restTemplate, permissionName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Permission createdPermission = createResponse.getBody();
        assertThat(createdPermission).isNotNull();

        // Update the permission's description
        String newDescription = "Updated description";
        createdPermission.setDescription(newDescription);
        HttpEntity<Permission> requestUpdate = new HttpEntity<>(createdPermission);
        ResponseEntity<Permission> updateResponse = restTemplate.exchange("/api/permissions/" + createdPermission.getPermissionId(), HttpMethod.PUT, requestUpdate, Permission.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getDescription()).isEqualTo(newDescription);

        // Verify the permission is updated in the database
        ResponseEntity<Permission> getResponse = restTemplate.getForEntity("/api/permissions/" + createdPermission.getPermissionId(), Permission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeletePermission() {
        // Create a permission first
        String permissionName = RandomString.make(20);
        ResponseEntity<Permission> createResponse = GenericCallsUsermanagement.createPermission(restTemplate, permissionName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Permission createdPermission = createResponse.getBody();
        assertThat(createdPermission).isNotNull();

        // Delete the permission
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/permissions/" + createdPermission.getPermissionId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the permission is deleted
        ResponseEntity<Permission> getResponse = restTemplate.getForEntity("/api/permissions/" + createdPermission.getPermissionId(), Permission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetPermissionById() {
        // Create a permission first
        String permissionName = RandomString.make(20);
        ResponseEntity<Permission> createResponse = GenericCallsUsermanagement.createPermission(restTemplate, permissionName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Permission createdPermission = createResponse.getBody();
        assertThat(createdPermission).isNotNull();

        // Retrieve the permission by ID
        ResponseEntity<Permission> getResponse = restTemplate.getForEntity("/api/permissions/" + createdPermission.getPermissionId(), Permission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo(permissionName);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetPermissionByIdWithInvalidUUID() {
        // Attempt to retrieve a permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Permission> getResponse = restTemplate.getForEntity("/api/permissions/" + invalidUUID, Permission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeletePermissionWithInvalidUUID() {
        // Attempt to delete a permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/permissions/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdatePermissionWithInvalidUUID() {
        // Attempt to update a permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        Permission permission = new Permission();
        permission.setDescription("Updated description");
        HttpEntity<Permission> requestUpdate = new HttpEntity<>(permission);
        ResponseEntity<Permission> updateResponse = restTemplate.exchange("/api/permissions/" + invalidUUID, HttpMethod.PUT, requestUpdate, Permission.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

