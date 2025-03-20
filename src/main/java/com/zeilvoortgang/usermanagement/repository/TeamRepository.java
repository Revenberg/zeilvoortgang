package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.Team;
import com.zeilvoortgang.usermanagement.exception.DuplicateTeamException;

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
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Team> findTeamById(UUID teamId) {
        final String sql = "SELECT * FROM teams WHERE teamid = ?";
        List<Team> teams = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, teamId.toString());
                return ps;
            }
        }, new TeamRowMapper());
        return teams.stream().findFirst();
    }

    public Team saveTeam(Team team) {
        final String sql = "INSERT INTO teams (teamid, organizationId, name, description, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, team.getTeamId().toString(), team.getOrganizationId().toString(), team.getName(), team.getDescription(), team.getLastUpdateTMS(), team.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateTeamException("A team with the same name already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return team;
    }

    public Team updateTeam(Team team) {
        final String sql = "UPDATE teams SET organizationId = ?, name = ?, description = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE teamid = ?";
        jdbcTemplate.update(sql, team.getOrganizationId(), team.getName(), team.getDescription(), team.getLastUpdateTMS(), team.getLastUpdateIdentifier(), team.getTeamId().toString());
        return team;
    }

    public void deleteByTeamId(UUID teamId) {
        final String sql = "DELETE FROM teams WHERE teamid = ?";
        jdbcTemplate.update(sql, teamId.toString());
    }

    private static class TeamRowMapper implements RowMapper<Team> {
        @SuppressWarnings("null")
        @Override
        public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
            Team team = new Team();
            team.setTeamId(UUID.fromString(rs.getString("teamid")));
            team.setOrganizationId(UUID.fromString(rs.getString("organizationId")));
            team.setName(rs.getString("name"));
            team.setDescription(rs.getString("description"));
            team.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            team.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return team;
        }
    }
}