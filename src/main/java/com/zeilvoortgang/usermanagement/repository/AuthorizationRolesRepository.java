package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.AuthorizationRoles;
import com.zeilvoortgang.usermanagement.exception.DuplicateAuthorizationRoleException;

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
public class AuthorizationRolesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizationRolesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<AuthorizationRoles> findAuthorizationRoleById(UUID authorizationRoleId) {
        final String sql = "SELECT * FROM authorizationRoles WHERE authorizationRoleId = ?";
        List<AuthorizationRoles> roles = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, authorizationRoleId.toString());
                return ps;
            }
        }, new AuthorizationRolesRowMapper());
        return roles.stream().findFirst();
    }

    public AuthorizationRoles saveAuthorizationRole(AuthorizationRoles role) {
        final String sql = "INSERT INTO authorizationRoles (authorizationRoleId, roleName, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, role.getAuthorizationRoleId().toString(), role.getRoleName(), role.getLastUpdateTMS(), role.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateAuthorizationRoleException("An authorization role with the same ID already exists.");
        }
        return role;
    }

    public AuthorizationRoles updateAuthorizationRole(AuthorizationRoles role) {
        final String sql = "UPDATE authorizationRoles SET roleName = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE authorizationRoleId = ?";
        jdbcTemplate.update(sql, role.getRoleName(), role.getLastUpdateTMS(), role.getLastUpdateIdentifier(), role.getAuthorizationRoleId().toString());
        return role;
    }

    public void deleteAuthorizationRoleById(UUID authorizationRoleId) {
        final String sql = "DELETE FROM authorizationRoles WHERE authorizationRoleId = ?";
        jdbcTemplate.update(sql, authorizationRoleId.toString());
    }

    private static class AuthorizationRolesRowMapper implements RowMapper<AuthorizationRoles> {
        @Override
        public AuthorizationRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuthorizationRoles role = new AuthorizationRoles();
            role.setAuthorizationRoleId(UUID.fromString(rs.getString("authorizationRoleId")));
            role.setRoleName(rs.getString("roleName"));
            role.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            role.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return role;
        }
    }
}