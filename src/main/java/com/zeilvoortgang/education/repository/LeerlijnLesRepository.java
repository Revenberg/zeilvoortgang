package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LeerlijnLes;
import com.zeilvoortgang.education.exception.DuplicateLeerlijnLesException;

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
public class LeerlijnLesRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LeerlijnLesRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LeerlijnLes> findLeerlijnLesById(UUID id) {
        final String sql = "SELECT * FROM leerlijnLessen WHERE leerlijnLesId = ?";
        List<LeerlijnLes> leerlijnLesList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LeerlijnLesRowMapper());
        return leerlijnLesList.stream().findFirst();
    }

    public LeerlijnLes saveLeerlijnLes(LeerlijnLes leerlijnLes) {
        final String sql = "INSERT INTO leerlijnLessen (leerlijnLesId, leerlijnId, levelId, lesOrder, title, description, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, leerlijnLes.getLeerlijnLesId().toString(), leerlijnLes.getLeerlijnId().toString(), leerlijnLes.getLevelId().toString(),
                    leerlijnLes.getLesOrder(), leerlijnLes.getTitle(), leerlijnLes.getDescription(),
                    leerlijnLes.getLastUpdateTMS(), leerlijnLes.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLeerlijnLesException("A leerlijnLes with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the leerlijnLes.", e);
        }
        return leerlijnLes;
    }

    public LeerlijnLes updateLeerlijnLes(LeerlijnLes leerlijnLes) {
        final String sql = "UPDATE leerlijnLessen SET levelId = ?, lesOrder = ?, title = ?, description = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE leerlijnLesId = ?";
        jdbcTemplate.update(sql, leerlijnLes.getLevelId().toString(), leerlijnLes.getLesOrder(),
                leerlijnLes.getTitle(), leerlijnLes.getDescription(), leerlijnLes.getLastUpdateTMS(),
                leerlijnLes.getLastUpdateIdentifier(), leerlijnLes.getLeerlijnLesId().toString());
        return leerlijnLes;
    }

    public void deleteLeerlijnLesById(UUID id) {
        final String sql = "DELETE FROM leerlijnLessen WHERE leerlijnLesId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LeerlijnLesRowMapper implements RowMapper<LeerlijnLes> {
        @SuppressWarnings("null")
        @Override
        public LeerlijnLes mapRow(ResultSet rs, int rowNum) throws SQLException {
            LeerlijnLes leerlijnLes = new LeerlijnLes();
            leerlijnLes.setLeerlijnLesId(UUID.fromString(rs.getString("leerlijnLesId")));
            leerlijnLes.setLevelId(UUID.fromString(rs.getString("levelId")));
            leerlijnLes.setLesOrder(rs.getInt("lesOrder"));
            leerlijnLes.setTitle(rs.getString("title"));
            leerlijnLes.setDescription(rs.getString("description"));
            leerlijnLes.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            leerlijnLes.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return leerlijnLes;
        }
    }
}