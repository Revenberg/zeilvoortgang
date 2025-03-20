package com.zeilvoortgang.usermanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.zeilvoortgang.education.GenericCallsEducation;
import com.zeilvoortgang.education.entity.ProgressStatussen;
import com.zeilvoortgang.usermanagement.entity.AuthorizationGroups;
import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;
import com.zeilvoortgang.usermanagement.entity.Authorizations;
import com.zeilvoortgang.usermanagement.entity.Group;
import com.zeilvoortgang.usermanagement.entity.GroupPermission;
import com.zeilvoortgang.usermanagement.entity.Organization;
import com.zeilvoortgang.usermanagement.entity.Permission;
import com.zeilvoortgang.usermanagement.entity.Team;
import com.zeilvoortgang.usermanagement.entity.TeamPermission;
import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.usermanagement.entity.UserRoles;
import com.zeilvoortgang.education.entity.UserLevels;
import com.zeilvoortgang.usermanagement.entity.UserTeam;

import net.bytebuddy.utility.RandomString;

public class GenericCallsUsermanagement {

	public static ResponseEntity<User> createUser(TestRestTemplate restTemplate, String username) {
		User user = new User();
		user.setUsername(username);
		user.setPassword("password");
		user.setEmail(RandomString.make(20) + "@test.com");
		user.setLastUpdateIdentifier("system");
		ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);
		return response;
	}

	public static ResponseEntity<User> createUser(TestRestTemplate restTemplate) {
		return createUser(restTemplate, RandomString.make(20));
	}

	public static ResponseEntity<Team> createTeam(TestRestTemplate restTemplate, UUID organizationId, String teamName) {
		Team team = new Team();
		team.setOrganizationId(organizationId);
		team.setName(teamName);
		team.setDescription(RandomString.make(20));
		team.setLastUpdateIdentifier("system");

		ResponseEntity<Team> response = restTemplate.postForEntity("/api/teams", team, Team.class);
		return response;
	}

	public static ResponseEntity<Team> createTeam(TestRestTemplate restTemplate) {

		ResponseEntity<Organization> organization = createOrganization(restTemplate, RandomString.make(20));

		@SuppressWarnings("null")
		UUID organizationId = organization.getBody().getOrganizationId();

		return createTeam(restTemplate, organizationId, RandomString.make(20));
	}

	public static ResponseEntity<UserTeam> createUserTeam(TestRestTemplate restTemplate, UUID userId, UUID teamId) {

		UserTeam userTeam = new UserTeam();
		userTeam.setUserId(userId);
		userTeam.setTeamId(teamId);
		userTeam.setLastUpdateIdentifier("system");

		ResponseEntity<UserTeam> response = restTemplate.postForEntity("/api/user-teams", userTeam, UserTeam.class);
		return response;
	}

	public static ResponseEntity<Permission> createPermission(TestRestTemplate restTemplate, String GroupId) {
		Permission permission = new Permission();
		permission.setName(GroupId);
		permission.setDescription("A test permission");
		permission.setLastUpdateIdentifier("system");

		ResponseEntity<Permission> response = restTemplate.postForEntity("/api/permissions", permission,
				Permission.class);
		return response;
	}

	public static ResponseEntity<Permission> createPermission(TestRestTemplate restTemplate) {
		return createPermission(restTemplate, RandomString.make(20));
	}

	public static ResponseEntity<GroupPermission> createGroupPermission(TestRestTemplate restTemplate,
			UUID permissionId, UUID groupId) {

		GroupPermission groupPermission = new GroupPermission();
		groupPermission.setPermissionId(permissionId);
		groupPermission.setGroupId(groupId);
		groupPermission.setLastUpdateIdentifier("system");

		ResponseEntity<GroupPermission> response = restTemplate.postForEntity("/api/group-permissions", groupPermission,
				GroupPermission.class);

		return response;
	}

	public static ResponseEntity<TeamPermission> createTeamPermission(TestRestTemplate restTemplate, UUID teamId,
			UUID groupPermissionId) {
		TeamPermission teamPermission = new TeamPermission();
		teamPermission.setTeamId(teamId);
		teamPermission.setGroupPermissionId(groupPermissionId);
		teamPermission.setLastUpdateIdentifier("system");

		ResponseEntity<TeamPermission> response = restTemplate.postForEntity("/api/team-permissions", teamPermission,
				TeamPermission.class);

		return response;
	}

	public static ResponseEntity<Group> createGroup(TestRestTemplate restTemplate, String groupName) {
		Group group = new Group();
		group.setName(groupName);
		group.setLastUpdateIdentifier("system");

		ResponseEntity<Group> response = restTemplate.postForEntity("/api/groups", group, Group.class);

		return response;
	}

	public static ResponseEntity<Organization> createOrganization(TestRestTemplate restTemplate, String name) {
		Organization organization = new Organization();
		organization.setName(name);
		organization.setLastUpdateIdentifier("system");

		return restTemplate.postForEntity("/api/organizations", organization, Organization.class);
	}

	public static ResponseEntity<UserRoles> createUserRole(TestRestTemplate restTemplate, UUID userId,
			UUID authorizationRoleId) {
		UserRoles userRole = new UserRoles();
		userRole.setUserId(userId);
		userRole.setAuthorizationRoleId(authorizationRoleId);
		userRole.setLastUpdateIdentifier("system");
		ResponseEntity<UserRoles> response = restTemplate.postForEntity("/api/user-roles", userRole, UserRoles.class);
		return response;
	}

	@SuppressWarnings("null")
	public static ResponseEntity<UserRoles> createUserRole(TestRestTemplate restTemplate) {
		ResponseEntity<User> user = GenericCallsUsermanagement.createUser(restTemplate);
		assertThat(user.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(user.getBody()).isNotNull();
		assertThat(user.getBody().getUserId()).isNotNull();
		UUID userId = user.getBody().getUserId();

		ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement
				.createAuthorizationRole(restTemplate);
		assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(authorizationRole.getBody()).isNotNull();
		assertThat(authorizationRole.getBody().getAuthorizationRoleId()).isNotNull();
		UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();
		ResponseEntity<UserRoles> response = createUserRole(restTemplate, userId, authorizationRoleId);
		return response;
	}

	public static ResponseEntity<AuthorizationGroups> createAuthorizationGroup(TestRestTemplate restTemplate,
			UUID authorizationId, UUID authorizationRoleId) {
		AuthorizationGroups authorizationGroup = new AuthorizationGroups();
		authorizationGroup.setAuthorizationId(authorizationId);
		authorizationGroup.setAuthorizationRoleId(authorizationRoleId);
		authorizationGroup.setLastUpdateIdentifier("system");
		ResponseEntity<AuthorizationGroups> response = restTemplate.postForEntity("/api/authorizationGroups",
				authorizationGroup, AuthorizationGroups.class);
		return response;
	}

	@SuppressWarnings("null")
	public static ResponseEntity<AuthorizationGroups> createAuthorizationGroup(TestRestTemplate restTemplate) {
		// Create an authorization group first
		ResponseEntity<Authorizations> authorization = GenericCallsUsermanagement.createAuthorization(restTemplate);
		assertThat(authorization.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(authorization.getBody()).isNotNull();
		UUID authorizationId = authorization.getBody().getAuthorizationId();

		ResponseEntity<AuthorizationRoles> authorizationRole = GenericCallsUsermanagement
				.createAuthorizationRole(restTemplate);
		assertThat(authorizationRole.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(authorizationRole.getBody()).isNotNull();
		UUID authorizationRoleId = authorizationRole.getBody().getAuthorizationRoleId();

		ResponseEntity<AuthorizationGroups> createResponse = GenericCallsUsermanagement
				.createAuthorizationGroup(restTemplate, authorizationId, authorizationRoleId);
		return createResponse;
	}

	public static ResponseEntity<AuthorizationRoles> createAuthorizationRole(TestRestTemplate restTemplate,
			String roleName) {
		AuthorizationRoles authorizationRole = new AuthorizationRoles();
		authorizationRole.setRoleName(roleName);
		authorizationRole.setLastUpdateIdentifier("system");
		ResponseEntity<AuthorizationRoles> response = restTemplate.postForEntity("/api/authorization-roles",
				authorizationRole, AuthorizationRoles.class);
		return response;
	}

	public static ResponseEntity<AuthorizationRoles> createAuthorizationRole(TestRestTemplate restTemplate) {
		String roleName = RandomString.make(20);

		ResponseEntity<AuthorizationRoles> response = createAuthorizationRole(restTemplate, roleName);
		return response;
	}

	public static ResponseEntity<Authorizations> createAuthorization(TestRestTemplate restTemplate, String description,
			String page, String raci) {
		Authorizations authorization = new Authorizations();
		authorization.setDescription(description);
		authorization.setPage(page);
		authorization.setRaci(raci);
		authorization.setLastUpdateIdentifier("system");
		ResponseEntity<Authorizations> response = restTemplate.postForEntity("/api/authorizations", authorization,
				Authorizations.class);
		return response;

	}

	public static ResponseEntity<Authorizations> createAuthorization(TestRestTemplate restTemplate) {
		String description = RandomString.make(20);
		String page = RandomString.make(15);
		String raci = "Responsible";

		ResponseEntity<Authorizations> response = createAuthorization(restTemplate, description, page, raci);
		return response;
	}
}
