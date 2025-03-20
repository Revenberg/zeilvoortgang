package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.Permission;
import com.zeilvoortgang.usermanagement.exception.DuplicatePermissionException;

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
public class PermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Permission> findPermissionById(UUID id) {
        final String sql = "SELECT * FROM permissions WHERE permissionId = ?";
        List<Permission> permissions = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new PermissionRowMapper());
        return permissions.stream().findFirst();
    }

    public Permission savePermission(Permission permission) {
        final String sql = "INSERT INTO permissions (permissionId, name, description, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, permission.getPermissionId().toString(), permission.getName(), permission.getDescription(), permission.getLastUpdateTMS(), permission.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicatePermissionException("A permission with the same name already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return permission;
    }

    public Permission updatePermission(Permission permission) {
        final String sql = "UPDATE permissions SET name = ?, description = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE permissionId = ?";
        jdbcTemplate.update(sql, permission.getName(), permission.getDescription(), permission.getLastUpdateTMS(), permission.getLastUpdateIdentifier(), permission.getPermissionId().toString());
        return permission;
    }

    public void deleteByPermissionId(UUID permissionId) {
        final String sql = "DELETE FROM permissions WHERE permissionId = ?";
        jdbcTemplate.update(sql, permissionId.toString());
    }

    private static class PermissionRowMapper implements RowMapper<Permission> {
        @SuppressWarnings("null")
        @Override
        public Permission mapRow(ResultSet rs, int rowNum) throws SQLException {
            Permission permission = new Permission();
            permission.setPermissionId(UUID.fromString(rs.getString("permissionId")));
            permission.setName(rs.getString("name"));
            permission.setDescription(rs.getString("description"));
            permission.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            permission.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return permission;
        }
    }
}