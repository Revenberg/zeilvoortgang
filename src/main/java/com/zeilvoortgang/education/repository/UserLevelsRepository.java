package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.UserLevels;
import com.zeilvoortgang.education.exception.DuplicateUserLevelsException;

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
public class UserLevelsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserLevelsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserLevels> findUserLevelsById(UUID id) {
        final String sql = "SELECT * FROM userLevels WHERE userLevelsId = ?";
        List<UserLevels> userLevelsList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new UserLevelsRowMapper());
        return userLevelsList.stream().findFirst();
    }

    public UserLevels saveUserLevels(UserLevels userLevels) {
        final String sql = "INSERT INTO userLevels (userLevelsId, userId, levelId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";        
        try {
            jdbcTemplate.update(sql, userLevels.getUserLevelsId().toString(), userLevels.getUserId().toString(), userLevels.getLevelId().toString(), userLevels.getLastUpdateTMS(), userLevels.getLastUpdateIdentifier());
            
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateUserLevelsException("A userLevel with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the userLevel.", e);
        }
        return userLevels;
    }

    public UserLevels updateUserLevels(UserLevels userLevels) {
        final String sql = "UPDATE userLevels SET userId = ?, levelId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE userLevelsId = ?";
        jdbcTemplate.update(sql, userLevels.getUserId().toString(), userLevels.getLevelId().toString(), userLevels.getLastUpdateTMS(), userLevels.getLastUpdateIdentifier(), userLevels.getUserLevelsId().toString());
        return userLevels;
    }

    public void deleteByUserLevelsId(UUID id) {
        final String sql = "DELETE FROM userLevels WHERE userLevelsId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class UserLevelsRowMapper implements RowMapper<UserLevels> {
        @SuppressWarnings("null")
            @Override
        public UserLevels mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserLevels userLevels = new UserLevels();
            userLevels.setUserLevelsId(UUID.fromString(rs.getString("userLevelsId")));
            userLevels.setUserId(UUID.fromString(rs.getString("userId")));
            userLevels.setLevelId(UUID.fromString(rs.getString("levelId")));
            userLevels.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            userLevels.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return userLevels;
        }
    }
}