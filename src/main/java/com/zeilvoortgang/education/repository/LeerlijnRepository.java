package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.Leerlijn;
import com.zeilvoortgang.education.exception.DuplicateLeerlijnException;

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
public class LeerlijnRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LeerlijnRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Leerlijn> findLeerlijnById(UUID id) {
        final String sql = "SELECT * FROM leerlijnen WHERE leerlijnId = ?";
        List<Leerlijn> leerlijnList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LeerlijnRowMapper());
        return leerlijnList.stream().findFirst();
    }

    public Leerlijn saveLeerlijn(Leerlijn leerlijn) {
        final String sql = "INSERT INTO leerlijnen (leerlijnId, levelId, title, description, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, leerlijn.getLeerlijnId().toString(), leerlijn.getLevelId().toString(), leerlijn.getTitle(), leerlijn.getDescription(), leerlijn.getLastUpdateTMS(), leerlijn.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLeerlijnException("A leerlijn with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the leerlijn.", e);
        }
        return leerlijn;
    }

    public Leerlijn updateLeerlijn(Leerlijn leerlijn) {
        final String sql = "UPDATE leerlijnen SET levelId = ?, title = ?, description = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE leerlijnId = ?";
        jdbcTemplate.update(sql, leerlijn.getLevelId().toString(), leerlijn.getTitle(), leerlijn.getDescription(), leerlijn.getLastUpdateTMS(), leerlijn.getLastUpdateIdentifier(), leerlijn.getLeerlijnId().toString());
        return leerlijn;
    }

    public void deleteLeerlijnById(UUID id) {
        final String sql = "DELETE FROM leerlijnen WHERE leerlijnId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LeerlijnRowMapper implements RowMapper<Leerlijn> {
        @SuppressWarnings("null")
        @Override
        public Leerlijn mapRow(ResultSet rs, int rowNum) throws SQLException {
            Leerlijn leerlijn = new Leerlijn();
            leerlijn.setLeerlijnId(UUID.fromString(rs.getString("leerlijnId")));
            leerlijn.setTitle(rs.getString("title"));
            leerlijn.setDescription(rs.getString("description"));
            leerlijn.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            leerlijn.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return leerlijn;
        }
    }
}