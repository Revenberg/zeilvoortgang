package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LesCursisten;
import com.zeilvoortgang.education.exception.DuplicateLesCursistenException;

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
public class LesCursistenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesCursistenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LesCursisten> findLesCursistenById(UUID id) {
        final String sql = "SELECT * FROM lesCursisten WHERE lesCursistenId= ?";
        List<LesCursisten> lesCursistenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesCursistenRowMapper());
        return lesCursistenList.stream().findFirst();
    }

    public LesCursisten saveLesCursisten(LesCursisten lesCursisten) {
        final String sql = "INSERT INTO lesCursisten (lesCursistenId, lesId, cursistId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, lesCursisten.getLesCursistenId().toString(), lesCursisten.getLesId().toString(), lesCursisten.getCursistId().toString(), lesCursisten.getLastUpdateTMS(), lesCursisten.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLesCursistenException("A lesCursist with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the lesCursist.", e);
        }
        return lesCursisten;
    }

    public LesCursisten updateLesCursisten(LesCursisten lesCursisten) {
        final String sql = "UPDATE lesCursisten SET lesId = ?, cursistId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesCursistenId= ?";
        jdbcTemplate.update(sql, lesCursisten.getLesId().toString(), lesCursisten.getCursistId().toString(), lesCursisten.getLastUpdateTMS(), lesCursisten.getLastUpdateIdentifier(), lesCursisten.getLesId().toString());
        return lesCursisten;
    }

    public void deleteByLesCursistenId(UUID id) {
        final String sql = "DELETE FROM lesCursisten WHERE lesCursistenId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesCursistenRowMapper implements RowMapper<LesCursisten> {
        @SuppressWarnings("null")
            @Override
        public LesCursisten mapRow(ResultSet rs, int rowNum) throws SQLException {
            LesCursisten lesCursisten = new LesCursisten();
            lesCursisten.setLesId(UUID.fromString(rs.getString("lesId")));
            lesCursisten.setCursistId(UUID.fromString(rs.getString("cursistId")));
            lesCursisten.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            lesCursisten.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return lesCursisten;
        }
    }
}