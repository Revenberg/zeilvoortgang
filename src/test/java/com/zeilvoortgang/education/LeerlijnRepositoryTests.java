package com.zeilvoortgang.education;

import com.zeilvoortgang.education.entity.Leerlijn;
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
class LeerlijnRepositoryTests {

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
     void testSaveLeerlijn() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Leerlijn> createResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);

        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Leerlijn createdLeerlijn = createResponse.getBody();

        assertThat(createdLeerlijn.getLevelId()).isEqualTo(levelId);
        assertThat(createdLeerlijn.getTitle()).isEqualTo(title);
        assertThat(createdLeerlijn.getDescription()).isEqualTo(description);
    }

    @SuppressWarnings("null")
    @Test 
     void testFindLeerlijnById() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Leerlijn> createResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);

        ResponseEntity<Leerlijn> response = restTemplate.getForEntity("/api/leerlijn/" + createResponse.getBody().getLeerlijnId().toString(), Leerlijn.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        assertThat(response.getBody().getLeerlijnId()).isEqualTo(createResponse.getBody().getLeerlijnId());
        assertThat(response.getBody().getTitle()).isEqualTo(createResponse.getBody().getTitle());
        assertThat(response.getBody().getDescription()).isEqualTo(createResponse.getBody().getDescription());
    }

    @SuppressWarnings("null")
    @Test 
     void testUpdateLeerlijn() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Leerlijn> createResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);

        Leerlijn createdLeerlijn = createResponse.getBody();
        String newTitle = RandomString.make(20);
        String newDescription = RandomString.make(20);
        createdLeerlijn.setTitle(newTitle);
        createdLeerlijn.setDescription(newDescription);

        HttpEntity<Leerlijn> requestUpdate = new HttpEntity<>(createdLeerlijn);
        ResponseEntity<Leerlijn> updateResponse = restTemplate.exchange(
                "/api/leerlijn/" + createdLeerlijn.getLeerlijnId().toString(),
                HttpMethod.PUT, requestUpdate, Leerlijn.class);

        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updateResponse.getBody()).isNotNull();
        Leerlijn updatedLeerlijn = updateResponse.getBody();
        assertThat(updatedLeerlijn.getTitle()).isEqualTo(newTitle);
        assertThat(updatedLeerlijn.getDescription()).isEqualTo(newDescription);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLeerlijnById() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Leerlijn> createResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);

        restTemplate.delete("/api/leerlijn/" + createResponse.getBody().getLeerlijnId().toString());

        ResponseEntity<Leerlijn> response = restTemplate.getForEntity("/api/leerlijn/" + createResponse.getBody().getLeerlijnId().toString(), Leerlijn.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @SuppressWarnings("null")
    @Test 
     void testCreateDuplicateLeerlijn() {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level.getBody()).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);

        ResponseEntity<Leerlijn> createResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Attempt to create a duplicate leerlijn
        ResponseEntity<Leerlijn> duplicateResponse = GenericCallsEducation.createLeerlijn(restTemplate, levelId, title, description);
        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @SuppressWarnings("null")
    @Test 
     void testGetLeerlijnByInvalidId() {
        // Attempt to retrieve a leerlijn with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Leerlijn> response = restTemplate.getForEntity("/api/leerlijn/" + invalidId, Leerlijn.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings("null")
    @Test 
     void testDeleteLeerlijnWithInvalidId() {
        // Attempt to delete a leerlijn with an invalid ID
        String invalidId = RandomString.make(20);
        ResponseEntity<Void> response = restTemplate.exchange("/api/leerlijn/" + invalidId, HttpMethod.DELETE, null, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}