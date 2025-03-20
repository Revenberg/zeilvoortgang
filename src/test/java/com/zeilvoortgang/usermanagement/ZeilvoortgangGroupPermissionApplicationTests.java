package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Group;
import com.zeilvoortgang.usermanagement.entity.GroupPermission;
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
class ZeilvoortgangGroupPermissionApplicationTests {

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
     void testCreateGroupPermission() {
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        assertThat(group.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        assertThat(permission.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<GroupPermission> response = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPermissionId()).isEqualTo(permission.getBody().getPermissionId());
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateGroupPermission() {
        // Create a permission and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> createResponse = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        GroupPermission createdGroupPermission = createResponse.getBody();
        assertThat(createdGroupPermission).isNotNull();

        // Update the group permission's description
        HttpEntity<GroupPermission> requestUpdate = new HttpEntity<>(createdGroupPermission);
        ResponseEntity<GroupPermission> updateResponse = restTemplate.exchange(
                "/api/group-permissions/" + createdGroupPermission.getGroupPermissionId(), HttpMethod.PUT,
                requestUpdate, GroupPermission.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();

        // Verify the group permission is updated in the database
        ResponseEntity<GroupPermission> getResponse = restTemplate.getForEntity(
                "/api/group-permissions/" + createdGroupPermission.getGroupPermissionId(), GroupPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteGroupPermission() {
        // Create a permission and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> createResponse = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        GroupPermission createdGroupPermission = createResponse.getBody();
        assertThat(createdGroupPermission).isNotNull();

        // Delete the group permission
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/group-permissions/" + createdGroupPermission.getGroupPermissionId(), HttpMethod.DELETE, null,
                Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the group permission is deleted
        ResponseEntity<GroupPermission> getResponse = restTemplate.getForEntity(
                "/api/group-permissions/" + createdGroupPermission.getGroupPermissionId(), GroupPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetGroupPermissionById() {
        // Create a permission and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> createResponse = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        GroupPermission createdGroupPermission = createResponse.getBody();
        assertThat(createdGroupPermission).isNotNull();

        // Retrieve the group permission by ID
        ResponseEntity<GroupPermission> getResponse = restTemplate.getForEntity(
                "/api/group-permissions/" + createdGroupPermission.getGroupPermissionId(), GroupPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getPermissionId()).isEqualTo(permission.getBody().getPermissionId());
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateGroupPermission() {
        // Create a permission and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> createResponse = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another group permission with the same permission ID
        ResponseEntity<GroupPermission> duplicateResponse = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetGroupPermissionByIdWithInvalidUUID() {
        // Attempt to retrieve a group permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<GroupPermission> getResponse = restTemplate.getForEntity("/api/group-permissions/" + invalidUUID, GroupPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteGroupPermissionWithInvalidUUID() {
        // Attempt to delete a group permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/group-permissions/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateGroupPermissionWithInvalidUUID() {
        // Attempt to update a group permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        GroupPermission groupPermission = new GroupPermission();
        groupPermission.setDescription("Updated description");
        HttpEntity<GroupPermission> requestUpdate = new HttpEntity<>(groupPermission);
        ResponseEntity<GroupPermission> updateResponse = restTemplate.exchange("/api/group-permissions/" + invalidUUID, HttpMethod.PUT, requestUpdate, GroupPermission.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
