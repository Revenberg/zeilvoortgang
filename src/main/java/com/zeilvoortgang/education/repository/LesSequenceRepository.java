package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LesSequence;
import com.zeilvoortgang.education.exception.DuplicateLesSequenceException;

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
public class LesSequenceRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesSequenceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LesSequence> findLesSequenceById(UUID id) {
try {
        final String sql = "SELECT * FROM lesSequence WHERE lesSequenceId = ?";
        List<LesSequence> lesSequenceList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesSequenceRowMapper());
        return lesSequenceList.stream().findFirst();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("An unexpected error occurred while saving the lesSequence.", e);
    }
    }

    public LesSequence saveLesSequence(LesSequence lesSequence) {
        final String sql = "INSERT INTO lesSequence (lesSequenceId, title, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, lesSequence.getLesSequenceId().toString(), lesSequence.getTitle(), lesSequence.getLastUpdateTMS(), lesSequence.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateLesSequenceException("A lesSequence with the same ID already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the lesSequence.", e);
        }
        return lesSequence;
    }

    public LesSequence updateLesSequence(LesSequence lesSequence) {
        try {
            final String sql = "UPDATE lesSequence SET title = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesSequenceId = ?";
        jdbcTemplate.update(sql, lesSequence.getTitle(), lesSequence.getLastUpdateTMS(), lesSequence.getLastUpdateIdentifier(), lesSequence.getLesSequenceId().toString());
    } catch (Exception e) {
        System.out.println(e.getMessage());
        throw new RuntimeException("An unexpected error occurred while saving the lesSequence.", e);
    }
    return lesSequence;
    }

    public void deleteByLesSequenceId(UUID id) {
        final String sql = "DELETE FROM lesSequence WHERE lesSequenceId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesSequenceRowMapper implements RowMapper<LesSequence> {
        @SuppressWarnings("null")
        @Override
        public LesSequence mapRow(ResultSet rs, int rowNum) throws SQLException {
            LesSequence lesSequence = new LesSequence();
            lesSequence.setLesSequenceId(UUID.fromString(rs.getString("lesSequenceId")));
            lesSequence.setTitle(rs.getString("title"));
            lesSequence.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            lesSequence.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return lesSequence;
        }
    }
}