package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.UserRoles;
import com.zeilvoortgang.usermanagement.exception.DuplicateUserRoleException;

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
public class UserRolesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRolesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserRoles> findUserRoleById(UUID userRolesId) {
        final String sql = "SELECT * FROM userRoles WHERE userRolesId = ?";
        List<UserRoles> userRoles = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, userRolesId.toString());
                return ps;
            }
        }, new UserRolesRowMapper());
        return userRoles.stream().findFirst();
    }

    public UserRoles saveUserRole(UserRoles userRole) {
        final String sql = "INSERT INTO userRoles (userRolesId, userId, authorizationRoleId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, userRole.getUserRolesId().toString(), userRole.getUserId().toString(), userRole.getAuthorizationRoleId().toString(), userRole.getLastUpdateTMS(), userRole.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserRoleException("A user role with the same ID already exists.");
        }
        return userRole;
    }

    public UserRoles updateUserRole(UserRoles userRole) {
        final String sql = "UPDATE userRoles SET userId = ?, authorizationRoleId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE userRolesId = ?";
        try {
        jdbcTemplate.update(sql, userRole.getUserId().toString(), userRole.getAuthorizationRoleId().toString(), userRole.getLastUpdateTMS(), userRole.getLastUpdateIdentifier(), userRole.getUserRolesId().toString());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateUserRoleException("A user role with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An error occurred while updating the user role.");
        }


        return userRole;
    }

    public void deleteUserRoleById(UUID userRolesId) {
        final String sql = "DELETE FROM userRoles WHERE userRolesId = ?";
        jdbcTemplate.update(sql, userRolesId.toString());
    }

    private static class UserRolesRowMapper implements RowMapper<UserRoles> {
        @Override
        public UserRoles mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserRoles userRole = new UserRoles();
            userRole.setUserRolesId(UUID.fromString(rs.getString("userRolesId")));
            userRole.setUserId(UUID.fromString(rs.getString("userId")));
            userRole.setAuthorizationRoleId(UUID.fromString(rs.getString("authorizationRoleId")));
            userRole.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            userRole.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return userRole;
        }
    }
}