package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LesMethodieken;
import com.zeilvoortgang.education.exception.DuplicateLesMethodiekenException;

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
public class LesMethodiekenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesMethodiekenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LesMethodieken> findLesMethodiekenById(UUID id) {
        final String sql = "SELECT * FROM lesmethodieken WHERE lesMethodiekId = ?";
        List<LesMethodieken> lesMethodiekenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesMethodiekenRowMapper());
        return lesMethodiekenList.stream().findFirst();
    }

    public LesMethodieken saveLesMethodieken(LesMethodieken lesMethodieken) {
        final String sql = "INSERT INTO lesmethodieken (lesMethodiekId, lesId, methodiekId, doelId, description, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, lesMethodieken.getLesMethodiekenId().toString(), lesMethodieken.getLesId().toString(), lesMethodieken.getMethodiekId().toString(), lesMethodieken.getDoelId().toString(), lesMethodieken.getDescription(), lesMethodieken.getLastUpdateTMS(), lesMethodieken.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateLesMethodiekenException("A lesMethodiek with the same ID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the lesMethodiek.", e);
        }
        return lesMethodieken;
    }

    public LesMethodieken updateLesMethodieken(LesMethodieken lesMethodieken) {
        final String sql = "UPDATE lesmethodieken SET lesId = ?, methodiekId = ?, doelId = ?, description = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesMethodiekId = ?";
        jdbcTemplate.update(sql, lesMethodieken.getLesId().toString(), lesMethodieken.getMethodiekId().toString(), lesMethodieken.getDoelId().toString(), lesMethodieken.getDescription(), lesMethodieken.getLastUpdateTMS(), lesMethodieken.getLastUpdateIdentifier(), lesMethodieken.getLesMethodiekenId().toString());
        return lesMethodieken;
    }

    public void deleteByLesMethodiekenId(UUID id) {
        final String sql = "DELETE FROM lesmethodieken WHERE lesMethodiekId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesMethodiekenRowMapper implements RowMapper<LesMethodieken> {
        @SuppressWarnings("null")
            @Override
        public LesMethodieken mapRow(ResultSet rs, int rowNum) throws SQLException {
            LesMethodieken lesMethodieken = new LesMethodieken();            
            lesMethodieken.setLesMethodiekenId(UUID.fromString(rs.getString("lesMethodiekId")));
            lesMethodieken.setDoelId(UUID.fromString(rs.getString("doelId")));
            lesMethodieken.setLesId(UUID.fromString(rs.getString("lesId")));
            lesMethodieken.setMethodiekId(UUID.fromString(rs.getString("methodiekId")));
            lesMethodieken.setDoelId(UUID.fromString(rs.getString("doelId")));
            lesMethodieken.setDescription(rs.getString("description"));
            lesMethodieken.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            lesMethodieken.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return lesMethodieken;
        }
    }
}