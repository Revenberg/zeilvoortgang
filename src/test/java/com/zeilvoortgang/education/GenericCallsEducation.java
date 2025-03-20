package com.zeilvoortgang.education;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zeilvoortgang.education.entity.Leerlijn;
import com.zeilvoortgang.education.entity.LeerlijnLes;
import com.zeilvoortgang.education.entity.LeerlijnLesDetails;
import com.zeilvoortgang.education.entity.Les;
import com.zeilvoortgang.education.entity.LesCursisten;
import com.zeilvoortgang.education.entity.LesMethodieken;
import com.zeilvoortgang.education.entity.LesSequence;
import com.zeilvoortgang.education.entity.LesStatussen;
import com.zeilvoortgang.education.entity.LesTrainers;
import com.zeilvoortgang.education.entity.Levels;
import com.zeilvoortgang.education.entity.Methodieken;
import com.zeilvoortgang.education.entity.ProgressStatussen;
import com.zeilvoortgang.education.entity.UserLevels;
import com.zeilvoortgang.education.entity.VoortgangStatussen;
import com.zeilvoortgang.usermanagement.GenericCallsUsermanagement;
import com.zeilvoortgang.usermanagement.entity.User;

import net.bytebuddy.utility.RandomString;

public class GenericCallsEducation {
    static UUID genericLevelId = null;
    static UUID genericLesSequenceId = null;
    static UUID genericLesId = null;

    @SuppressWarnings("null")
    public static UUID getGenericLevelId(TestRestTemplate restTemplate) {
        if (genericLevelId == null) {
            ResponseEntity<Levels> level = createLevels(restTemplate, "TestLevelId" + RandomString.make(20));
            genericLevelId = level.getBody().getLevelId();
        }
        return genericLevelId;
    }

    @SuppressWarnings("null")
    public static UUID getGenericLesSequenceId(TestRestTemplate restTemplate) {
        if (genericLesSequenceId == null) {
            ResponseEntity<LesSequence> lesSequence = createLesSequence(restTemplate,
                    "TestLesSequence" + RandomString.make(20));
            genericLesSequenceId = lesSequence.getBody().getLesSequenceId();
        }
        return genericLesSequenceId;
    }

    @SuppressWarnings("null")
    public static UUID getGenericLesId(TestRestTemplate restTemplate) {
        if (genericLesId == null) {
            ResponseEntity<Les> les = createLes(restTemplate);
            genericLesId = les.getBody().getLesId();
        }
        return genericLesId;
    }

    public static ResponseEntity<LesSequence> createLesSequence(TestRestTemplate restTemplate, String title) {
        LesSequence lesSequence = new LesSequence();
        lesSequence.setTitle(title);
        lesSequence.setLastUpdateIdentifier("system");

        ResponseEntity<LesSequence> response = restTemplate.postForEntity("/api/lesSequence", lesSequence,
                LesSequence.class);
        return response;
    }

    public static ResponseEntity<LesSequence> createLesSequence(TestRestTemplate restTemplate) {
        return createLesSequence(restTemplate, RandomString.make(20));
    }

    public static ResponseEntity<Levels> createLevels(TestRestTemplate restTemplate, String description) {
        Levels levels = new Levels();
        levels.setDescription(description);
        levels.setImage("test_image.png");

        ResponseEntity<Levels> response = restTemplate.postForEntity("/api/levels", levels, Levels.class);
        return response;
    }

    public static ResponseEntity<Levels> createLevels(TestRestTemplate restTemplate) {
        return createLevels(restTemplate, RandomString.make(20));
    }

    public static ResponseEntity<ProgressStatussen> createProgressStatussen(TestRestTemplate restTemplate,
            String description,
            String longDescription) {
        ProgressStatussen progressStatussen = new ProgressStatussen();
        progressStatussen.setDescription(description);
        progressStatussen.setLongDescription(longDescription);

        ResponseEntity<ProgressStatussen> response = restTemplate.postForEntity("/api/progressStatussen",
                progressStatussen, ProgressStatussen.class);
        return response;
    }

    public static ResponseEntity<ProgressStatussen> createProgressStatussen(TestRestTemplate restTemplate) {
        String description = RandomString.make(5);
        String longDescription = RandomString.make(20);
        return createProgressStatussen(restTemplate, description, longDescription);
    }

    public static ResponseEntity<LesMethodieken> createLesMethodieken(TestRestTemplate restTemplate, UUID lesId,
            UUID methodiekId, UUID doelId, String description) {
        LesMethodieken lesMethodieken = new LesMethodieken();
        lesMethodieken.setLesId(lesId);
        lesMethodieken.setMethodiekId(methodiekId);
        lesMethodieken.setDoelId(doelId);
        lesMethodieken.setDescription(description);
        lesMethodieken.setLastUpdateIdentifier("system");

        ResponseEntity<LesMethodieken> response = restTemplate.postForEntity("/api/lesMethodieken", lesMethodieken,
                LesMethodieken.class);

        return response;
    }

    public static ResponseEntity<Methodieken> createMethodieken(TestRestTemplate restTemplate, String methodiek,
            String description) {
        Methodieken methodieken = new Methodieken();
        methodieken.setDescription(description);
        methodieken.setLevelId(getGenericLevelId(restTemplate));
        methodieken.setMethodiek(methodiek);
        methodieken.setSoort("Test Soort");
        methodieken.setLastUpdateIdentifier("system");

        ResponseEntity<Methodieken> response = restTemplate.postForEntity("/api/methodieken", methodieken,
                Methodieken.class);
        return response;
    }

    public static ResponseEntity<Methodieken> createMethodieken(TestRestTemplate restTemplate) {
        return createMethodieken(restTemplate, RandomString.make(20), RandomString.make(20));
    }

    public static ResponseEntity<Les> createLes(TestRestTemplate restTemplate, String title, String description) {
        UUID lesSequenceId = GenericCallsEducation.getGenericLesSequenceId(restTemplate);

        Les les = new Les();
        les.setLesSequenceId(lesSequenceId);
        les.setLesDateTime(new Timestamp(System.currentTimeMillis()));
        les.setTitle(title);
        les.setDescription(description);
        les.setStatus("Test");
        les.setAfgesloten(null);
        les.setLastUpdateIdentifier("system");

        ResponseEntity<Les> createResponse = restTemplate.postForEntity("/api/les", les, Les.class);
        return createResponse;
    }

    public static ResponseEntity<Les> createLes(TestRestTemplate restTemplate) {
        return createLes(restTemplate, RandomString.make(20), RandomString.make(20));
    }

    public static ResponseEntity<LesCursisten> createLesCursisten(TestRestTemplate restTemplate, UUID lesId,
            UUID cursistId) {

        LesCursisten lesCursisten = new LesCursisten();
        lesCursisten.setLesId(lesId);
        lesCursisten.setCursistId(cursistId);
        lesCursisten.setLastUpdateIdentifier("system");

        ResponseEntity<LesCursisten> response = restTemplate.postForEntity("/api/lesCursisten", lesCursisten,
                LesCursisten.class);

        return response;
    }

    public static ResponseEntity<LesCursisten> createLesCursisten(TestRestTemplate restTemplate) {
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        @SuppressWarnings("null")
        UUID cursistId = user.getBody().getUserId();

        return createLesCursisten(restTemplate, lesId, cursistId);
    }

    public static ResponseEntity<LesTrainers> createLesTrainers(TestRestTemplate restTemplate, UUID lesId,
            UUID trainerId) {

        LesTrainers lesTrainers = new LesTrainers();
        lesTrainers.setLesId(lesId);
        lesTrainers.setTrainerId(trainerId);
        lesTrainers.setLastUpdateIdentifier("system");

        ResponseEntity<LesTrainers> response = restTemplate.postForEntity("/api/lesTrainers", lesTrainers,
                LesTrainers.class);

        return response;
    }

    public static ResponseEntity<LesTrainers> createLesTrainers(TestRestTemplate restTemplate) {
        UUID lesId = GenericCallsEducation.getGenericLesId(restTemplate);

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        @SuppressWarnings("null")
        UUID trainerId = user.getBody().getUserId();

        return createLesTrainers(restTemplate, lesId, trainerId);
    }

    public static ResponseEntity<LesStatussen> createLesStatus(TestRestTemplate restTemplate, UUID lesId,
            UUID methodiekId,
            UUID userId, UUID progressId) {
        LesStatussen lesStatussen = new LesStatussen();
        lesStatussen.setLesId(lesId);
        lesStatussen.setMethodiekId(methodiekId);
        lesStatussen.setUserId(userId);
        lesStatussen.setProgressId(progressId);
        lesStatussen.setLastUpdateIdentifier("system");

        ResponseEntity<LesStatussen> response = restTemplate.postForEntity("/api/lesStatussen", lesStatussen,
                LesStatussen.class);

        return response;
    }

    public static ResponseEntity<Leerlijn> createLeerlijn(TestRestTemplate restTemplate, UUID progressStatusId,
            String title, String description) {
        Leerlijn leerlijn = new Leerlijn();
        leerlijn.setTitle(title);
        leerlijn.setDescription(description);
        leerlijn.setLevelId(progressStatusId);

        leerlijn.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        leerlijn.setLastUpdateIdentifier("system");

        ResponseEntity<Leerlijn> response = restTemplate.postForEntity("/api/leerlijn", leerlijn, Leerlijn.class);
        return response;
    }

    public static ResponseEntity<Leerlijn> createLeerlijn(TestRestTemplate restTemplate, UUID levelId) {
        String title = RandomString.make(20);
        String description = RandomString.make(20);

        return createLeerlijn(restTemplate, levelId, title, description);
    }

    public static ResponseEntity<LeerlijnLes> createLeerlijnLes(TestRestTemplate restTemplate, UUID leerlijnId,
            UUID levelId,
            int lesOrder,
            String title, String description) {
        LeerlijnLes leerlijnLes = new LeerlijnLes();
        leerlijnLes.setLeerlijnId(leerlijnId);
        leerlijnLes.setLevelId(levelId);
        leerlijnLes.setLesOrder(lesOrder);
        leerlijnLes.setTitle(title);
        leerlijnLes.setDescription(description);
        leerlijnLes.setLastUpdateTMS(new Timestamp(System.currentTimeMillis()));
        leerlijnLes.setLastUpdateIdentifier("system");

        ResponseEntity<LeerlijnLes> response = restTemplate.postForEntity("/api/leerlijnLes", leerlijnLes,
                LeerlijnLes.class);
        return response;
    }

    @SuppressWarnings("null")
    public static ResponseEntity<LeerlijnLes> createLeerlijnLes(TestRestTemplate restTemplate) {
        ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(level).isNotNull();
        UUID levelId = level.getBody().getLevelId();

        ResponseEntity<Leerlijn> leerlijn = GenericCallsEducation.createLeerlijn(restTemplate, levelId);
        assertThat(leerlijn.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijn.getBody()).isNotNull();
        UUID leerlijnId = leerlijn.getBody().getLeerlijnId();

        String title = RandomString.make(20);
        String description = RandomString.make(20);
        int lesOrder = new Random().nextInt(100);
        return createLeerlijnLes(restTemplate, leerlijnId, levelId, lesOrder, title, description);
    }

    public static ResponseEntity<LeerlijnLesDetails> createLeerlijnLesDetails(TestRestTemplate restTemplate,
            UUID leerlijnLesId, UUID methodiekId, UUID progressID) {
        LeerlijnLesDetails leerlijnLesDetails = new LeerlijnLesDetails();

        leerlijnLesDetails.setLeerlijnLesId(leerlijnLesId);
        leerlijnLesDetails.setMethodiekId(methodiekId);
        leerlijnLesDetails.setProgressStatusId(progressID);
        leerlijnLesDetails.setLastUpdateIdentifier("system");

        ResponseEntity<LeerlijnLesDetails> response = restTemplate.postForEntity("/api/leerlijnLesDetails",
                leerlijnLesDetails, LeerlijnLesDetails.class);
        return response;
    }

    @SuppressWarnings("null")
    public static ResponseEntity<LeerlijnLesDetails> createLeerlijnLesDetails(TestRestTemplate restTemplate) {

        ResponseEntity<ProgressStatussen> createResponse = GenericCallsEducation.createProgressStatussen(restTemplate);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        UUID progressId = createResponse.getBody().getProgressStatusId();

        ResponseEntity<LeerlijnLes> leerlijnLes = GenericCallsEducation.createLeerlijnLes(restTemplate);
        assertThat(leerlijnLes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leerlijnLes.getBody()).isNotNull();
        UUID leerlijnLesId = leerlijnLes.getBody().getLeerlijnLesId();

        ResponseEntity<Methodieken> methodiek = GenericCallsEducation.createMethodieken(restTemplate);
        assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(methodiek.getBody()).isNotNull();
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        return createLeerlijnLesDetails(restTemplate, leerlijnLesId, methodiekId, progressId);
    }

    public static ResponseEntity<VoortgangStatussen> createVoortgangStatussen(TestRestTemplate restTemplate, UUID lesId,
            UUID methodiekId, UUID userId, UUID progressId) {
        VoortgangStatussen voortgangStatussen = new VoortgangStatussen();
        voortgangStatussen.setLesId(lesId);
        voortgangStatussen.setMethodiekId(methodiekId);
        voortgangStatussen.setUserId(userId);
        voortgangStatussen.setProgressId(progressId);
        voortgangStatussen.setLastUpdateIdentifier("system");

        ResponseEntity<VoortgangStatussen> response = restTemplate.postForEntity("/api/voortgangStatussen",
                voortgangStatussen, VoortgangStatussen.class);

                return response;
    }

    @SuppressWarnings("null")
    public static ResponseEntity<VoortgangStatussen> createVoortgangStatussen(TestRestTemplate restTemplate) {

        ResponseEntity<Les> les = createLes(restTemplate);
        assertThat(les.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(les.getBody()).isNotNull();
        UUID lesId = les.getBody().getLesId();

        ResponseEntity<Methodieken> methodiek = createMethodieken(restTemplate);
        assertThat(methodiek.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(methodiek.getBody()).isNotNull();
        UUID methodiekId = methodiek.getBody().getMethodiekId();

        ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
        assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(user.getBody()).isNotNull();
        UUID userId = user.getBody().getUserId();

        ResponseEntity<ProgressStatussen> progress = createProgressStatussen(restTemplate);
        assertThat(progress.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(progress.getBody()).isNotNull();
        UUID progressId = progress.getBody().getProgressStatusId();

        ResponseEntity<VoortgangStatussen> response = createVoortgangStatussen(restTemplate, lesId, methodiekId, userId, progressId);
        return response;
    }

    public static ResponseEntity<UserLevels> createUserLevels(TestRestTemplate restTemplate, UUID userId, UUID levelId) {
		UserLevels userLevels = new UserLevels();
		userLevels.setUserId(userId);
		userLevels.setLevelId(levelId);
		userLevels.setLastUpdateIdentifier("system");

		return restTemplate.postForEntity("/api/userLevels", userLevels, UserLevels.class);
	}

	@SuppressWarnings("null")
	public static ResponseEntity<UserLevels> createUserLevels(TestRestTemplate restTemplate) {
		ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
		assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
		UUID userId = user.getBody().getUserId();

		ResponseEntity<Levels> level = GenericCallsEducation.createLevels(restTemplate);        
        assertThat(level.getStatusCode()).isEqualTo(HttpStatus.OK);
        UUID levelId = level.getBody().getLevelId();

		return createUserLevels(restTemplate, userId, levelId);
	}
}