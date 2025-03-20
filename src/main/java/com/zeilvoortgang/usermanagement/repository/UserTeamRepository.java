package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.UserTeam;
import com.zeilvoortgang.usermanagement.exception.DuplicateUserTeamException;

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
public class UserTeamRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserTeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserTeam> findUserTeamById(UUID id) {
        final String sql = "SELECT * FROM user_teams WHERE userTeamId = ?";
        List<UserTeam> userTeams = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new UserTeamRowMapper());
        return userTeams.stream().findFirst();
    }

    public List<UserTeam> findUserTeamByTeamId(UUID id) {
        final String sql = "SELECT * FROM user_teams WHERE teamId = ?";
        List<UserTeam> userTeams = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new UserTeamRowMapper());
        return userTeams;
    }

    public List<UserTeam> findUserTeamByUserId(UUID id) {
        final String sql = "SELECT * FROM user_teams WHERE userId = ?";
        List<UserTeam> userTeams = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new UserTeamRowMapper());
        return userTeams;
    }

    public UserTeam saveUserTeam(UserTeam userTeam) {
        final String sql = "INSERT INTO user_teams (userTeamId, userId, teamId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, userTeam.getUserTeamId().toString(), userTeam.getUserId().toString(), userTeam.getTeamId().toString(), userTeam.getLastUpdateTMS(), userTeam.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserTeamException("A user team with the same ID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return userTeam;
    }

    public void deleteByUserTeamId(UUID id) {
        final String sql = "DELETE FROM user_teams WHERE userTeamId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class UserTeamRowMapper implements RowMapper<UserTeam> {
        @SuppressWarnings("null")
        @Override
        public UserTeam mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserTeam userTeam = new UserTeam();
            userTeam.setUserTeamId(UUID.fromString(rs.getString("userTeamId")));
            userTeam.setUserId(UUID.fromString(rs.getString("userId")));
            userTeam.setTeamId(UUID.fromString(rs.getString("teamId")));
            userTeam.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            userTeam.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return userTeam;
        }
    }
}