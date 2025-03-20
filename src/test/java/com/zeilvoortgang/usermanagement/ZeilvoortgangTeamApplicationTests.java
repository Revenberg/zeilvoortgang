package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Organization;
import com.zeilvoortgang.usermanagement.entity.Team;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class ZeilvoortgangTeamApplicationTests {

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
     void testCreateTeam() {
        String teamName = RandomString.make(20);

        ResponseEntity<Organization> organisation = GenericCallsUsermanagement.createOrganization( restTemplate, RandomString.make(20)); 
        assertThat(organisation.getStatusCode()).isEqualTo(HttpStatus.OK);

        ResponseEntity<Team> response = GenericCallsUsermanagement.createTeam(restTemplate, organisation.getBody().getOrganizationId(), teamName);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo(teamName);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteTeam() {
        // Create a team first
        String teamName = RandomString.make(20);
        ResponseEntity<Organization> organisation = GenericCallsUsermanagement.createOrganization( restTemplate,  RandomString.make(20)); 

        ResponseEntity<Team> createResponse = GenericCallsUsermanagement.createTeam(restTemplate, organisation.getBody().getOrganizationId(), teamName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Team createdTeam = createResponse.getBody();
        assertThat(createdTeam).isNotNull();

        // Delete the team
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/teams/" + createdTeam.getTeamId(), HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the team is deleted
        ResponseEntity<Team> getResponse = restTemplate.getForEntity("/api/teams/" + createdTeam.getTeamId(), Team.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetTeamById() {
        // Create a team first
        String teamName = RandomString.make(20);

        ResponseEntity<Organization> organisation = GenericCallsUsermanagement.createOrganization( restTemplate,  RandomString.make(20)); 
        ResponseEntity<Team> createResponse = GenericCallsUsermanagement.createTeam(restTemplate, organisation.getBody().getOrganizationId(), teamName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Team createdTeam = createResponse.getBody();
        assertThat(createdTeam).isNotNull();

        // Retrieve the team by ID
        ResponseEntity<Team> getResponse = restTemplate.getForEntity("/api/teams/" + createdTeam.getTeamId(), Team.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getName()).isEqualTo(teamName);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateTeam() {
        // Create a team first
        String teamName = RandomString.make(20);
        
       ResponseEntity<Organization> organisation = GenericCallsUsermanagement.createOrganization( restTemplate,  RandomString.make(20)); 
       ResponseEntity<Team> createResponse = GenericCallsUsermanagement.createTeam(restTemplate, organisation.getBody().getOrganizationId(), teamName);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another team with the same name
        ResponseEntity<Team> duplicateResponse = GenericCallsUsermanagement.createTeam(restTemplate, organisation.getBody().getOrganizationId(), teamName);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetTeamByIdWithInvalidUUID() {
        // Attempt to retrieve a team with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Team> getResponse = restTemplate.getForEntity("/api/teams/" + invalidUUID, Team.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteTeamWithInvalidUUID() {
        // Attempt to delete a team with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/teams/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateTeamWithInvalidUUID() {
        // Attempt to update a team with an invalid UUID
        String invalidUUID = "invalid-uuid";
        Team team = new Team();
        team.setName(RandomString.make(20));
        HttpEntity<Team> requestUpdate = new HttpEntity<>(team);
        ResponseEntity<Team> updateResponse = restTemplate.exchange("/api/teams/" + invalidUUID, HttpMethod.PUT, requestUpdate, Team.class);
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
