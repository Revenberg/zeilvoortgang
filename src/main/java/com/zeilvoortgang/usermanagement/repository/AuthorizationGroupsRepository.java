package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.AuthorizationGroups;
import com.zeilvoortgang.usermanagement.exception.DuplicateAuthorizationGroupException;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorizationGroupsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizationGroupsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthorizationGroups> findAuthorizationGroupById(UUID authorizationGroupsId) {
        final String sql = "SELECT * FROM authorizationGroups WHERE authorizationGroupsId = ?";
        List<AuthorizationGroups> groups = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, authorizationGroupsId.toString());
                return ps;
            }
        }, new AuthorizationGroupsRowMapper());
        return groups.stream().findFirst();
    }

    public AuthorizationGroups saveAuthorizationGroup(AuthorizationGroups group) {
        final String sql = "INSERT INTO authorizationGroups (authorizationGroupsId, authorizationId, authorizationRoleId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {            
            jdbcTemplate.update(sql, group.getAuthorizationGroupsId().toString(), group.getAuthorizationId().toString(), group.getAuthorizationRoleId().toString(), group.getLastUpdateTMS(), group.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateAuthorizationGroupException("An authorization group with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while saving the authorization group.");
        }

        return group;
    }

    public AuthorizationGroups updateAuthorizationGroup(AuthorizationGroups group) {
        final String sql = "UPDATE authorizationGroups SET authorizationId = ?, authorizationRoleId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE authorizationGroupsId = ?";
        jdbcTemplate.update(sql, group.getAuthorizationId().toString(), group.getAuthorizationRoleId().toString(), group.getLastUpdateTMS(), group.getLastUpdateIdentifier(), group.getAuthorizationGroupsId().toString());
        return group;
    }

    public void deleteAuthorizationGroupById(UUID authorizationGroupsId) {
        final String sql = "DELETE FROM authorizationGroups WHERE authorizationGroupsId = ?";
        jdbcTemplate.update(sql, authorizationGroupsId.toString());
    }

    private static class AuthorizationGroupsRowMapper implements RowMapper<AuthorizationGroups> {
        @Override
        public AuthorizationGroups mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuthorizationGroups group = new AuthorizationGroups();
            group.setAuthorizationGroupsId(UUID.fromString(rs.getString("authorizationGroupsId")));
            group.setAuthorizationId(UUID.fromString(rs.getString("authorizationId")));
            group.setAuthorizationRoleId(UUID.fromString(rs.getString("authorizationRoleId")));
            group.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            group.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return group;
        }
    }
}