package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.TeamPermission;
import com.zeilvoortgang.usermanagement.exception.DuplicateTeamPermissionException;

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
public class TeamPermissionRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamPermissionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<TeamPermission> findTeamPermissionById(UUID teamId, UUID groupPermissionId) {
        final String sql = "SELECT * FROM team_permissions WHERE teamId = ? AND groupPermissionId = ?";
        List<TeamPermission> teamPermissions = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, teamId.toString());
                ps.setString(2, groupPermissionId.toString());
                return ps;
            }
        }, new TeamPermissionRowMapper());
        return teamPermissions.stream().findFirst();
    }

    public TeamPermission saveTeamPermission(TeamPermission teamPermission) {
        final String sql = "INSERT INTO team_permissions (teamId, groupPermissionId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, teamPermission.getTeamId().toString(), teamPermission.getGroupPermissionId().toString(), teamPermission.getLastUpdateTMS(), teamPermission.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateTeamPermissionException("A team permission with the same ID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return teamPermission;
    }

    public TeamPermission updateTeamPermission(TeamPermission teamPermission) {
        final String sql = "UPDATE team_permissions SET lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE teamId = ? AND groupPermissionId = ?";
        jdbcTemplate.update(sql, teamPermission.getLastUpdateTMS(), teamPermission.getLastUpdateIdentifier(), teamPermission.getTeamId().toString(), teamPermission.getGroupPermissionId().toString());
        return teamPermission;
    }

    public void deleteByTeamPermissionId(UUID teamId, UUID groupPermissionId) {
        final String sql = "DELETE FROM team_permissions WHERE teamId = ? AND groupPermissionId = ?";
        jdbcTemplate.update(sql, teamId.toString(), groupPermissionId.toString());
    }

    private static class TeamPermissionRowMapper implements RowMapper<TeamPermission> {
        @SuppressWarnings("null")
        @Override
        public TeamPermission mapRow(ResultSet rs, int rowNum) throws SQLException {
            TeamPermission teamPermission = new TeamPermission();
            teamPermission.setTeamId(UUID.fromString(rs.getString("teamId")));
            teamPermission.setGroupPermissionId(UUID.fromString(rs.getString("groupPermissionId")));
            teamPermission.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            teamPermission.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return teamPermission;
        }
    }
}