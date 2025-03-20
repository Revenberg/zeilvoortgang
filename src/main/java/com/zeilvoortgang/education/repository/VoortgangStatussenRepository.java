package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.VoortgangStatussen;
import com.zeilvoortgang.education.exception.DuplicateVoortgangStatussenException;

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
public class VoortgangStatussenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public VoortgangStatussenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<VoortgangStatussen> findVoortgangStatussenById(UUID id) {
        final String sql = "SELECT * FROM voortgangStatussen WHERE voortgangStatussenId = ?";
        List<VoortgangStatussen> voortgangStatussenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new VoortgangStatussenRowMapper());
        return voortgangStatussenList.stream().findFirst();
    }

    public VoortgangStatussen saveVoortgangStatussen(VoortgangStatussen voortgangStatussen) {
        final String sql = "INSERT INTO voortgangStatussen (voortgangStatussenId, lesId, methodiekId, userId, progressId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, voortgangStatussen.getVoortgangStatussenId().toString(), voortgangStatussen.getLesId().toString(), voortgangStatussen.getMethodiekId().toString(), voortgangStatussen.getUserId().toString(), voortgangStatussen.getProgressId().toString(), voortgangStatussen.getLastUpdateTMS(), voortgangStatussen.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateVoortgangStatussenException("A voortgangStatus with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the voortgangStatus.", e);
        }
        return voortgangStatussen;
    }

    public VoortgangStatussen updateVoortgangStatussen(VoortgangStatussen voortgangStatussen) {
        final String sql = "UPDATE voortgangStatussen SET lesId = ?, methodiekId = ?, userId = ?, progressId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE voortgangStatussenId = ?";
        jdbcTemplate.update(sql, voortgangStatussen.getLesId().toString(), voortgangStatussen.getMethodiekId().toString(), voortgangStatussen.getUserId().toString(), voortgangStatussen.getProgressId().toString(), voortgangStatussen.getLastUpdateTMS(), voortgangStatussen.getLastUpdateIdentifier(), voortgangStatussen.getVoortgangStatussenId().toString());
        return voortgangStatussen;
    }

    public void deleteByVoortgangStatussenId(UUID id) {
        final String sql = "DELETE FROM voortgangStatussen WHERE voortgangStatussenId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class VoortgangStatussenRowMapper implements RowMapper<VoortgangStatussen> {
        @SuppressWarnings("null")
            @Override
        public VoortgangStatussen mapRow(ResultSet rs, int rowNum) throws SQLException {
            VoortgangStatussen voortgangStatussen = new VoortgangStatussen();
            voortgangStatussen.setVoortgangStatussenId(UUID.fromString(rs.getString("voortgangStatussenId")));
            voortgangStatussen.setLesId(UUID.fromString(rs.getString("lesId")));
            voortgangStatussen.setMethodiekId(UUID.fromString(rs.getString("methodiekId")));
            voortgangStatussen.setUserId(UUID.fromString(rs.getString("userId")));
            voortgangStatussen.setProgressId(UUID.fromString(rs.getString("progressId")));
            voortgangStatussen.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            voortgangStatussen.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return voortgangStatussen;
        }
    }
}