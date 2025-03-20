package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Team;
import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.usermanagement.entity.UserTeam;

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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangUserTeamApplicationTests {

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
    void testFindUserTeamById() {
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> response = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(user.getBody().getUserId());
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());       

        // Retrieve the user team by ID
        ResponseEntity<UserTeam> getResponse = restTemplate.getForEntity(
                "/api/user-teams/" + response.getBody().getUserTeamId(), UserTeam.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getUserId()).isEqualTo(user.getBody().getUserId());
        assertThat(getResponse.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());
    }

    @SuppressWarnings("null")
    @Test 
    void testFindUserTeamByUserId() {
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> response = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(user.getBody().getUserId());
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());       

        // Retrieve the user team by ID
        ResponseEntity<List<UserTeam>> getResponse = restTemplate.exchange(
                "/api/user-teams/user/" + response.getBody().getUserId(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserTeam>>() {});
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isOfAnyClassIn(ArrayList.class);
    }

    @SuppressWarnings("null")
    @Test 
    void testFindUserTeamByTeamId() {
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> response = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(user.getBody().getUserId());
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());       

        // Retrieve the user team by ID
        ResponseEntity<List<UserTeam>> getResponse = restTemplate.exchange(
                "/api/user-teams/user/" + response.getBody().getUserId(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<UserTeam>>() {});
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isOfAnyClassIn(ArrayList.class);
    }



    @SuppressWarnings("null")
    @Test 
    void testCreateUserTeam() {
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> response = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUserId()).isEqualTo(user.getBody().getUserId());
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());
    }

    @SuppressWarnings("null")
    @Test 
    void testDeleteUserTeam() {
        // Create a user and team first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> createResponse = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserTeam createdUserTeam = createResponse.getBody();
        assertThat(createdUserTeam).isNotNull();

        // Delete the user team
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "/api/user-teams/" + createdUserTeam.getUserTeamId(), HttpMethod.DELETE,
                null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the user team is deleted
        ResponseEntity<UserTeam> getResponse = restTemplate.getForEntity(
                "/api/user-teams/" + createdUserTeam.getUserId() + "/" + createdUserTeam.getTeamId(), UserTeam.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
    void testCreateDuplicateUserTeam() {
        // Create a user and team first
        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<UserTeam> createResponse = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another user team with the same user ID and team ID
        ResponseEntity<UserTeam> duplicateResponse = GenericCallsUsermanagement.createUserTeam(restTemplate,
                user.getBody().getUserId(), team.getBody().getTeamId());
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}