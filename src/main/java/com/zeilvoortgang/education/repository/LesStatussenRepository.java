package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LesStatussen;
import com.zeilvoortgang.education.exception.DuplicateLesStatussenException;

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
public class LesStatussenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesStatussenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LesStatussen> findLesStatussenById(UUID id) {
        final String sql = "SELECT * FROM lesStatussen WHERE lesStatusId = ?";
        List<LesStatussen> lesStatussenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesStatussenRowMapper());
        return lesStatussenList.stream().findFirst();
    }

    public LesStatussen saveLesStatussen(LesStatussen lesStatussen) {
        final String sql = "INSERT INTO lesStatussen (lesStatusId, lesId, methodiekId, userId, progressId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, lesStatussen.getLesStatusId().toString(), lesStatussen.getLesId().toString(), lesStatussen.getMethodiekId().toString(), lesStatussen.getUserId().toString(), lesStatussen.getProgressId().toString(), lesStatussen.getLastUpdateTMS(), lesStatussen.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLesStatussenException("A lesStatus with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the lesStatus.", e);
        }
        return lesStatussen;
    }

    public LesStatussen updateLesStatussen(LesStatussen lesStatussen) {
        final String sql = "UPDATE lesStatussen SET lesId = ?, methodiekId = ?, userId = ?, progressId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesStatusId = ?";
        jdbcTemplate.update(sql, lesStatussen.getLesId().toString(), lesStatussen.getMethodiekId().toString(), lesStatussen.getUserId().toString(), lesStatussen.getProgressId().toString(), lesStatussen.getLastUpdateTMS(), lesStatussen.getLastUpdateIdentifier(), lesStatussen.getLesStatusId().toString());
        return lesStatussen;
    }

    public void deleteByLesStatusId(UUID id) {
        final String sql = "DELETE FROM lesStatussen WHERE lesStatusId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesStatussenRowMapper implements RowMapper<LesStatussen> {
        @SuppressWarnings("null")
            @Override
        public LesStatussen mapRow(ResultSet rs, int rowNum) throws SQLException {
            LesStatussen lesStatussen = new LesStatussen();
            lesStatussen.setLesStatusId(UUID.fromString(rs.getString("lesStatusId")));
            lesStatussen.setLesId(UUID.fromString(rs.getString("lesId")));
            lesStatussen.setMethodiekId(UUID.fromString(rs.getString("methodiekId")));
            lesStatussen.setUserId(UUID.fromString(rs.getString("userId")));
            lesStatussen.setProgressId(UUID.fromString(rs.getString("progressId")));
            lesStatussen.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            lesStatussen.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return lesStatussen;
        }
    }
}