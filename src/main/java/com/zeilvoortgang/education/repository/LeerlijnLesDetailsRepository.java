package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LeerlijnLesDetails;
import com.zeilvoortgang.education.exception.DuplicateLeerlijnLesDetailsException;

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
public class LeerlijnLesDetailsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LeerlijnLesDetailsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LeerlijnLesDetails> findLeerlijnLesDetailsById(UUID id) {
        final String sql = "SELECT * FROM leerlijnLesDetails WHERE leerlijnLesDetailsId = ?";
        List<LeerlijnLesDetails> leerlijnLesDetailsList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LeerlijnLesDetailsRowMapper());
        return leerlijnLesDetailsList.stream().findFirst();
    }

    public LeerlijnLesDetails saveLeerlijnLesDetails(LeerlijnLesDetails leerlijnLesDetails) {
        final String sql = "INSERT INTO leerlijnLesDetails (leerlijnLesDetailsId, leerlijnLesId, methodiekId, progressStatusId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, leerlijnLesDetails.getLeerlijnLesDetailsId().toString(),
                    leerlijnLesDetails.getLeerlijnLesId().toString(), leerlijnLesDetails.getMethodiekId().toString(),
                    leerlijnLesDetails.getProgressStatusId().toString(), leerlijnLesDetails.getLastUpdateTMS(),
                    leerlijnLesDetails.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLeerlijnLesDetailsException("A leerlijnLesDetails with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the leerlijnLesDetails.", e);
        }
        return leerlijnLesDetails;
    }

    public LeerlijnLesDetails updateLeerlijnLesDetails(LeerlijnLesDetails leerlijnLesDetails) {
        final String sql = "UPDATE leerlijnLesDetails SET progressStatusId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE leerlijnLesDetailsId = ?";
        jdbcTemplate.update(sql, leerlijnLesDetails.getProgressStatusId().toString(),
                leerlijnLesDetails.getLastUpdateTMS(), leerlijnLesDetails.getLastUpdateIdentifier(),
                leerlijnLesDetails.getLeerlijnLesDetailsId().toString());
        return leerlijnLesDetails;
    }

    public void deleteLeerlijnLesDetailsById(UUID id) {
        try {
            final String sql = "DELETE FROM leerlijnLesDetails WHERE leerlijnLesDetailsId = ?";
            jdbcTemplate.update(sql, id.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while deleting the leerlijnLesDetails.", e);
        }
    }

    private static class LeerlijnLesDetailsRowMapper implements RowMapper<LeerlijnLesDetails> {
        @SuppressWarnings("null")
        @Override
        public LeerlijnLesDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            LeerlijnLesDetails leerlijnLesDetails = new LeerlijnLesDetails();
            leerlijnLesDetails.setLeerlijnLesDetailsId(UUID.fromString(rs.getString("leerlijnLesDetailsId")));
            leerlijnLesDetails.setLeerlijnLesId(UUID.fromString(rs.getString("leerlijnLesId")));
            leerlijnLesDetails.setMethodiekId(UUID.fromString(rs.getString("methodiekId")));
            leerlijnLesDetails.setProgressStatusId(UUID.fromString(rs.getString("progressStatusId")));
            leerlijnLesDetails.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            leerlijnLesDetails.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return leerlijnLesDetails;
        }
    }
}