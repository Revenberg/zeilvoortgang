package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.GroupPermission;
import com.zeilvoortgang.usermanagement.exception.DuplicateGroupPermissionException;

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
public class GroupPermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupPermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<GroupPermission> findGroupPermissionById(UUID id) {
        final String sql = "SELECT * FROM group_permissions WHERE groupPermissionId = ?";
        List<GroupPermission> groupPermissions = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new GroupPermissionRowMapper());
        return groupPermissions.stream().findFirst();
    }

    public GroupPermission saveGroupPermission(GroupPermission groupPermission) {
        final String sql = "INSERT INTO group_permissions (groupPermissionId, groupId, permissionId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, groupPermission.getGroupPermissionId().toString(), groupPermission.getGroupId().toString(), groupPermission.getPermissionId().toString(), groupPermission.getLastUpdateTMS(), groupPermission.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateGroupPermissionException("A group permission with the same ID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return groupPermission;
    }

    public GroupPermission updateGroupPermission(GroupPermission groupPermission) {
        final String sql = "UPDATE group_permissions SET permissionId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE groupPermissionId = ?";
        jdbcTemplate.update(sql, groupPermission.getPermissionId().toString(), groupPermission.getLastUpdateTMS(), groupPermission.getLastUpdateIdentifier(), groupPermission.getGroupPermissionId().toString());
        return groupPermission;
    }

    public void deleteByGroupPermissionId(UUID groupPermissionId) {
        final String sql = "DELETE FROM group_permissions WHERE groupPermissionId = ?";
        jdbcTemplate.update(sql, groupPermissionId.toString());
    }

    private static class GroupPermissionRowMapper implements RowMapper<GroupPermission> {
        @SuppressWarnings("null")
        @Override
        public GroupPermission mapRow(ResultSet rs, int rowNum) throws SQLException {
            GroupPermission groupPermission = new GroupPermission();
            groupPermission.setGroupPermissionId(UUID.fromString(rs.getString("groupPermissionId")));
            groupPermission.setPermissionId(UUID.fromString(rs.getString("permissionId")));
            groupPermission.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            groupPermission.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return groupPermission;
        }
    }
}