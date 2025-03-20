package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.Les;
import com.zeilvoortgang.education.exception.DuplicateLesException;

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
public class LesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Les> findLesById(UUID id) {
        final String sql = "SELECT * FROM lessen WHERE lesId = ?";
        List<Les> lessen = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesRowMapper());
        return lessen.stream().findFirst();
    }

    public Les saveLes(Les les) {
        final String sql = "INSERT INTO lessen (lesId, lesSequenceId, lesDateTime, title, description, status, afgesloten, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, les.getLesId().toString(), les.getLesSequenceId().toString(), les.getLesDateTime(), les.getTitle(), les.getDescription(), les.getStatus(), les.getAfgesloten(), les.getLastUpdateTMS(), les.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateLesException("A les with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the les.", e);
        }
        return les;
    }

    public Les updateLes(Les les) {
        final String sql = "UPDATE lessen SET lesSequenceId = ?, lesDateTime = ?, title = ?, description = ?, status = ?, afgesloten = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesId = ?";
        jdbcTemplate.update(sql, les.getLesSequenceId().toString(), les.getLesDateTime(), les.getTitle(), les.getDescription(), les.getStatus(), les.getAfgesloten(), les.getLastUpdateTMS(), les.getLastUpdateIdentifier(), les.getLesId().toString());
        return les;
    }

    public void deleteByLesId(UUID id) {
        final String sql = "DELETE FROM lessen WHERE lesId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesRowMapper implements RowMapper<Les> {
        @SuppressWarnings("null")
            @Override
        public Les mapRow(ResultSet rs, int rowNum) throws SQLException {
            Les les = new Les();
            les.setLesId(UUID.fromString(rs.getString("lesId")));
            les.setLesSequenceId(UUID.fromString(rs.getString("lesSequenceId")));
            les.setLesDateTime(rs.getTimestamp("lesDateTime"));
            les.setTitle(rs.getString("title"));
            les.setDescription(rs.getString("description"));
            les.setStatus(rs.getString("status"));
            les.setAfgesloten(rs.getTimestamp("afgesloten"));
            les.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            les.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return les;
        }
    }
}