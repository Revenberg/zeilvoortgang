package com.zeilvoortgang.usermanagement;

import com.zeilvoortgang.usermanagement.entity.Group;
import com.zeilvoortgang.usermanagement.entity.GroupPermission;
import com.zeilvoortgang.usermanagement.entity.Permission;
import com.zeilvoortgang.usermanagement.entity.Team;
import com.zeilvoortgang.usermanagement.entity.TeamPermission;

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
class ZeilvoortgangTeamPermissionApplicationTests {

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
     void testCreateTeamPermission() {
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);

        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> groupPermission = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());

        ResponseEntity<TeamPermission> response = GenericCallsUsermanagement.createTeamPermission(restTemplate,
                team.getBody().getTeamId(), groupPermission.getBody().getGroupPermissionId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());
        assertThat(response.getBody().getGroupPermissionId())
                .isEqualTo(groupPermission.getBody().getGroupPermissionId());
    }

    @SuppressWarnings("null")
    @Test 
     void testFindTeamPermissionById() {
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> groupPermission = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        ResponseEntity<TeamPermission> response = GenericCallsUsermanagement.createTeamPermission(restTemplate,
                team.getBody().getTeamId(), groupPermission.getBody().getGroupPermissionId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());
        assertThat(response.getBody().getGroupPermissionId())
                .isEqualTo(groupPermission.getBody().getGroupPermissionId());

        // Retrieve the team permission by ID
        ResponseEntity<TeamPermission> getResponse = restTemplate.getForEntity("/api/team-permissions/"
                + team.getBody().getTeamId() + "/" + groupPermission.getBody().getGroupPermissionId(),
                TeamPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody().getTeamId()).isEqualTo(team.getBody().getTeamId());
        assertThat(getResponse.getBody().getGroupPermissionId())
                .isEqualTo(groupPermission.getBody().getGroupPermissionId());
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteTeamPermission() {
        // Create a team and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);

        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> groupPermission = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        ResponseEntity<TeamPermission> createResponse = GenericCallsUsermanagement.createTeamPermission(restTemplate,
                team.getBody().getTeamId(), groupPermission.getBody().getGroupPermissionId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TeamPermission createdTeamPermission = createResponse.getBody();
        assertThat(createdTeamPermission).isNotNull();

        // Delete the team permission
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/team-permissions/"
                + createdTeamPermission.getTeamId() + "/" + createdTeamPermission.getGroupPermissionId(),
                HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify the team permission is deleted
        ResponseEntity<TeamPermission> getResponse = restTemplate.getForEntity("/api/team-permissions/"
                + createdTeamPermission.getTeamId() + "/" + createdTeamPermission.getGroupPermissionId(),
                TeamPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateTeamPermission() {
        // Create a team and group permission first
        String groupName = RandomString.make(20);
        ResponseEntity<Group> group = GenericCallsUsermanagement.createGroup(restTemplate, groupName);
        ResponseEntity<Team> team = GenericCallsUsermanagement.createTeam(restTemplate);
        ResponseEntity<Permission> permission = GenericCallsUsermanagement.createPermission(restTemplate);
        ResponseEntity<GroupPermission> groupPermission = GenericCallsUsermanagement.createGroupPermission(restTemplate,
                permission.getBody().getPermissionId(), group.getBody().getGroupId());
        ResponseEntity<TeamPermission> createResponse = GenericCallsUsermanagement.createTeamPermission(restTemplate,
                team.getBody().getTeamId(), groupPermission.getBody().getGroupPermissionId());
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Try to create another team permission with the same team ID and group
        // permission ID
        ResponseEntity<TeamPermission> duplicateResponse = GenericCallsUsermanagement.createTeamPermission(restTemplate,
                team.getBody().getTeamId(), groupPermission.getBody().getGroupPermissionId());
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetTeamPermissionByIdWithInvalidUUID() {
        // Attempt to retrieve a team permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<TeamPermission> getResponse = restTemplate.getForEntity("/api/team-permissions/" + invalidUUID + "/" + invalidUUID, TeamPermission.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteTeamPermissionWithInvalidUUID() {
        // Attempt to delete a team permission with an invalid UUID
        String invalidUUID = "invalid-uuid";
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/api/team-permissions/" + invalidUUID + "/" + invalidUUID, HttpMethod.DELETE, null, Void.class);
        assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}