package com.zeilvoortgang.usermanagement;

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

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangUserApplicationTests {

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
     void testCreateUser() {
        String username = RandomString.make(20);
        ResponseEntity<User> response = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo(username);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateUser() {
        // Create a user first
        String username = RandomString.make(20);
        ResponseEntity<User> createResponse = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        User createdUser = createResponse.getBody();
        assertThat(createdUser).isNotNull();

        // Update the user's email
        String newEmail = RandomString.make(20) + "@test.com";
        createdUser.setEmail(newEmail);
        HttpEntity<User> requestUpdate = new HttpEntity<>(createdUser);
        ResponseEntity<User> updateResponse = restTemplate.exchange("/api/users/" + createdUser.getUserId(), HttpMethod.PUT, requestUpdate, User.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        assertThat(updateResponse.getBody().getEmail()).isEqualTo(newEmail);

        // Verify the user is updated in the database
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.getUserId(), User.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getEmail()).isEqualTo(newEmail);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteUser() {
        // Create a user first
        String username = RandomString.make(20);
        ResponseEntity<User> createResponse = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        User createdUser = createResponse.getBody();
        assertThat(createdUser).isNotNull();

        // Delete the user
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/users/" + createdUser.getUserId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the user is deleted
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.getUserId(), User.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetUserById() {
        // Create a user first
        String username = RandomString.make(20);
        ResponseEntity<User> createResponse = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        User createdUser = createResponse.getBody();
        assertThat(createdUser).isNotNull();

        // Retrieve the user by ID
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/api/users/" + createdUser.getUserId(), User.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getUsername()).isEqualTo(username);
    }

    @SuppressWarnings("null")
    @Test 
     void testUserNotFound() {
        // Try to retrieve a user that does not exist
        UUID nonExistentUserId = UUID.randomUUID();
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/api/users/" + nonExistentUserId, User.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateUserWithSameUsername() {
        // Create a user first
        String username = RandomString.make(20);
        ResponseEntity<User> createResponse = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another user with the same username
        ResponseEntity<User> duplicateResponse = GenericCallsUsermanagement.createUser(restTemplate, username);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateUserWithSameEmail() {
        // Create a user first
        String email = RandomString.make(20) + "@test.com";
        User user = new User();
        user.setUsername(RandomString.make(20));
        user.setPassword("password");
        user.setEmail(email);
        user.setLastUpdateIdentifier("system");

        ResponseEntity<User> createResponse = restTemplate.postForEntity("/api/users", user, User.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another user with the same email
        user.setUsername(RandomString.make(20));
        ResponseEntity<User> duplicateResponse = restTemplate.postForEntity("/api/users", user, User.class);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateUserWithSameUsername() {
        // Create two users
        String username1 = RandomString.make(20);
        ResponseEntity<User> createResponse1 = GenericCallsUsermanagement.createUser(restTemplate, username1);
        assertThat(createResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        User user1 = createResponse1.getBody();
        assertThat(user1).isNotNull();

        String username2 = RandomString.make(20);
        ResponseEntity<User> createResponse2 = GenericCallsUsermanagement.createUser(restTemplate, username2);
        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        User user2 = createResponse2.getBody();
        assertThat(user2).isNotNull();

        // Try to update user2 with the same username as user1
        user2.setUsername(username1);
        HttpEntity<User> requestUpdate = new HttpEntity<>(user2);
        ResponseEntity<User> updateResponse = restTemplate.exchange("/api/users/" + user2.getUserId(), HttpMethod.PUT, requestUpdate, User.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateUserWithSameEmail() {
        // Create two users
        String email1 = RandomString.make(20) + "@test.com";
        User user1 = new User();
        user1.setUsername(RandomString.make(20));
        user1.setPassword("password");
        user1.setEmail(email1);
        user1.setLastUpdateIdentifier("system");

        ResponseEntity<User> createResponse1 = restTemplate.postForEntity("/api/users", user1, User.class);
        assertThat(createResponse1.getStatusCode()).isEqualTo(HttpStatus.OK);
        user1 = createResponse1.getBody();
        assertThat(user1).isNotNull();

        String email2 = RandomString.make(20) + "@test.com";
        User user2 = new User();
        user2.setUsername(RandomString.make(20));
        user2.setPassword("password");
        user2.setEmail(email2);
        user2.setLastUpdateIdentifier("system");

        ResponseEntity<User> createResponse2 = restTemplate.postForEntity("/api/users", user2, User.class);
        assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.OK);
        user2 = createResponse2.getBody();
        assertThat(user2).isNotNull();

        // Try to update user2 with the same email as user1
        user2.setEmail(email1);
        HttpEntity<User> requestUpdate = new HttpEntity<>(user2);
        ResponseEntity<User> updateResponse = restTemplate.exchange("/api/users/" + user2.getUserId(), HttpMethod.PUT, requestUpdate, User.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetUserByIdWithInvalidUUID() {
        // Attempt to retrieve a user with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<User> getResponse = restTemplate.getForEntity("/api/users/" + invalidUUID, User.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteUserWithInvalidUUID() {
        // Attempt to delete a user with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/users/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateUserWithInvalidUUID() {
        // Attempt to update a user with an invalid UUID
        String invalidUUID = "invalid-uuid";
        User user = new User();
        user.setUsername(RandomString.make(20));
        user.setEmail(RandomString.make(20) + "@test.com");
        HttpEntity<User> requestUpdate = new HttpEntity<>(user);
        ResponseEntity<User> updateResponse = restTemplate.exchange("/api/users/" + invalidUUID, HttpMethod.PUT, requestUpdate, User.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
