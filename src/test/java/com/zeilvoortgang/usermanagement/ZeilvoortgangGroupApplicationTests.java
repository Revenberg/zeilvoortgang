package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Group;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangGroupApplicationTests {

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
     void testCreateGroup() {
        String groupName = "TestGroup_" + RandomString.make(20);

        ResponseEntity<Group> response = GenericCallsUsermanagement.createGroup(restTemplate, groupName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(groupName);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteGroup() {
        // Create a group first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> createResponse = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Group createdGroup = createResponse.getBody();
        assertThat(createdGroup).isNotNull();

        // Delete the group
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/groups/" + createdGroup.getGroupId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the group is deleted
        ResponseEntity<Group> getResponse = restTemplate.getForEntity("/api/groups/" + createdGroup.getGroupId(), Group.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetGroupById() {
        // Create a group first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> createResponse = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Group createdGroup = createResponse.getBody();
        assertThat(createdGroup).isNotNull();

        // Retrieve the group by ID
        ResponseEntity<Group> getResponse = restTemplate.getForEntity("/api/groups/" + createdGroup.getGroupId(), Group.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo(groupName);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateGroup() {
        // Create a group first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> createResponse = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another group with the same name
        ResponseEntity<Group> duplicateResponse = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetGroupByIdWithInvalidUUID() {
        // Attempt to retrieve a group with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Group> getResponse = restTemplate.getForEntity("/api/groups/" + invalidUUID, Group.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteGroupWithInvalidUUID() {
        // Attempt to delete a group with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/groups/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
