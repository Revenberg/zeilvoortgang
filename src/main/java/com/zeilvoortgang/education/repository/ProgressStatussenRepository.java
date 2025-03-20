package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.ProgressStatussen;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProgressStatussenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgressStatussenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<ProgressStatussen> findProgressStatussenById(UUID id) {
        final String sql = "SELECT * FROM progressStatussen WHERE progressStatusId = ?";
        List<ProgressStatussen> progressStatussenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setObject(1, id.toString());
                return ps;
            }
        }, new ProgressStatussenRowMapper());
        return progressStatussenList.stream().findFirst();
    }

    public ProgressStatussen saveProgressStatussen(ProgressStatussen progressStatussen) {
        final String sql = "INSERT INTO progressStatussen (progressStatusId, description, longDescription) VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, progressStatussen.getProgressStatusId().toString(), progressStatussen.getDescription(), progressStatussen.getLongDescription());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        
        return progressStatussen;
    }

    public ProgressStatussen updateProgressStatussen(ProgressStatussen progressStatussen) {
        final String sql = "UPDATE progressStatussen SET description = ?, longDescription = ? WHERE progressStatusId = ?";
        jdbcTemplate.update(sql, progressStatussen.getDescription(), progressStatussen.getLongDescription(), progressStatussen.getProgressStatusId().toString());
        return progressStatussen;
    }

    public void deleteProgressStatussenById(UUID id) {
        final String sql = "DELETE FROM progressStatussen WHERE progressStatusId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class ProgressStatussenRowMapper implements RowMapper<ProgressStatussen> {
        @SuppressWarnings("null")
        @Override
        public ProgressStatussen mapRow(ResultSet rs, int rowNum) throws SQLException {
            ProgressStatussen progressStatussen = new ProgressStatussen();
            progressStatussen.setProgressStatusId(UUID.fromString(rs.getString("progressStatusId")));
            progressStatussen.setDescription(rs.getString("description"));
            progressStatussen.setLongDescription(rs.getString("longDescription"));
            return progressStatussen;
        }
    }
}