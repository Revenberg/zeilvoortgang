package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Leerlijn;
import com.zeilvoortgang.education.entity.LeerlijnLes;
import com.zeilvoortgang.education.entity.Levels;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import org.springframework.http.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Nested
class LeerlijnLesRepositoryTests {

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
     void testSaveLeerlijnLes() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<Leerlijn> leerlijn = GenericCallsEducation.createLeerlijn(restTemplate, levelId);
        assertThat(leerlijn.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijn.getBody()).isNotNull();
        UUID leerlijnId = leerlijn.getBody().getLeerlijnId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);
        int lesOrder = (int) (Math.random() * 100);
        
        ResponseEntity<LeerlijnLes> createResponse = GenericCallsEducation.createLeerlijnLes(restTemplate, leerlijnId, levelId,
                lesOrder, title, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        LeerlijnLes createdLeerlijnLes = createResponse.getBody();

        assertThat(createdLeerlijnLes.getLevelId()).isEqualTo(levelId);
        assertThat(createdLeerlijnLes.getTitle()).isEqualTo(title);
        assertThat(createdLeerlijnLes.getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testFindLeerlijnLesById() {

        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<Leerlijn> leerlijn = GenericCallsEducation.createLeerlijn(restTemplate, levelId);
        assertThat(leerlijn.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijn.getBody()).isNotNull();
        UUID leerlijnId = leerlijn.getBody().getLeerlijnId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);
        int lesOrder = (int) (Math.random() * 100);
        
        ResponseEntity<LeerlijnLes> createResponse = GenericCallsEducation.createLeerlijnLes(restTemplate, leerlijnId, levelId,
                lesOrder, title, description);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        
        ResponseEntity<LeerlijnLes> response = restTemplate.getForEntity(
                "/api/leerlijnLes/" + createResponse.getBody().getLeerlijnLesId().toString(), LeerlijnLes.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getLeerlijnLesId()).isEqualTo(createResponse.getBody().getLeerlijnLesId());
        assertThat(response.getBody().getTitle()).isEqualTo(createResponse.getBody().getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(createResponse.getBody().getDescription());
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLeerlijnLes() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<Leerlijn> leerlijn = GenericCallsEducation.createLeerlijn(restTemplate, levelId);
        assertThat(leerlijn.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijn.getBody()).isNotNull();
        UUID leerlijnId = leerlijn.getBody().getLeerlijnId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);
        int lesOrder = (int) (Math.random() * 100);
        
        ResponseEntity<LeerlijnLes> createResponse = GenericCallsEducation.createLeerlijnLes(restTemplate, leerlijnId, levelId,
                lesOrder, title, description);

        LeerlijnLes createdLeerlijnLes = createResponse.getBody();
        String newTitle = RandomString.make(20);
        String newDescription = RandomString.make(20);
        createdLeerlijnLes.setTitle(newTitle);
        createdLeerlijnLes.setDescription(newDescription);

        HttpEntity<LeerlijnLes> requestUpdate = new HttpEntity<>(createdLeerlijnLes);
        ResponseEntity<LeerlijnLes> updateResponse = restTemplate.exchange(
                "/api/leerlijnLes/" + createdLeerlijnLes.getLeerlijnLesId().toString(),
                HttpMethod.PUT, requestUpdate, LeerlijnLes.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        LeerlijnLes updatedLeerlijnLes = updateResponse.getBody();
        assertThat(updatedLeerlijnLes.getTitle()).isEqualTo(newTitle);
        assertThat(updatedLeerlijnLes.getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLeerlijnLesById() {        
        ResponseEntity<LeerlijnLes> createResponse = GenericCallsEducation.createLeerlijnLes(restTemplate);

        restTemplate.delete("/api/leerlijnLes/" + createResponse.getBody().getLeerlijnLesId().toString());

        ResponseEntity<LeerlijnLes> response = restTemplate.getForEntity(
                "/api/leerlijnLes/" + createResponse.getBody().getLeerlijnLesId().toString(), LeerlijnLes.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLeerlijnLes() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<Leerlijn> leerlijn = GenericCallsEducation.createLeerlijn(restTemplate, levelId);
        assertThat(leerlijn.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijn.getBody()).isNotNull();
        UUID leerlijnId = leerlijn.getBody().getLeerlijnId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);
        int lesOrder = (int) (Math.random() * 100);
        
        ResponseEntity<LeerlijnLes> createResponse = GenericCallsEducation.createLeerlijnLes(restTemplate, leerlijnId, levelId,
                lesOrder, title, description);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Attempt to create a duplicate leerlijnLes       
                ResponseEntity<LeerlijnLes> duplicateResponse = GenericCallsEducation.createLeerlijnLes(restTemplate, leerlijnId, levelId,
                lesOrder, title, description);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLeerlijnLesByInvalidId() {
        // Attempt to retrieve a leerlijnLes with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<LeerlijnLes> response = restTemplate.getForEntity("/api/leerlijnLes/" + invalidId,
                LeerlijnLes.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLeerlijnLesWithInvalidId() {
        // Attempt to delete a leerlijnLes with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> response = restTemplate.exchange("/api/leerlijnLes/" + invalidId, HttpMethod.DELETE, null,
                Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}