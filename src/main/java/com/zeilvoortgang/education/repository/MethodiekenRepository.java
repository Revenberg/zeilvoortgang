package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.Methodieken;
import com.zeilvoortgang.education.exception.DuplicateMethodiekenException;

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
public class MethodiekenRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MethodiekenRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Methodieken> findMethodiekById(UUID id) {
        final String sql = "SELECT * FROM methodieken WHERE methodiekId = ?";
        List<Methodieken> methodiekenList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new MethodiekenRowMapper());
        return methodiekenList.stream().findFirst();
    }

    public Methodieken saveMethodiek(Methodieken methodiek) {
        final String sql = "INSERT INTO methodieken (methodiekId, levelId, description, methodiek, soort, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, methodiek.getMethodiekId().toString(), methodiek.getLevelId().toString(), methodiek.getDescription(), methodiek.getMethodiek(), methodiek.getSoort(), methodiek.getLastUpdateTMS(), methodiek.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateMethodiekenException("A methodiek with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An unexpected error occurred while saving the methodiek.", e);
        }
        return methodiek;
    }

    public Methodieken updateMethodiek(Methodieken methodiek) {
        final String sql = "UPDATE methodieken SET levelId = ?, description = ?, methodiek = ?, soort = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE methodiekId = ?";
        jdbcTemplate.update(sql, methodiek.getLevelId().toString(), methodiek.getDescription(), methodiek.getMethodiek(), methodiek.getSoort(), methodiek.getLastUpdateTMS(), methodiek.getLastUpdateIdentifier(), methodiek.getMethodiekId().toString());
        return methodiek;
    }

    public void deleteMethodiekById(UUID id) {
        final String sql = "DELETE FROM methodieken WHERE methodiekId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class MethodiekenRowMapper implements RowMapper<Methodieken> {
        @SuppressWarnings("null")
        @Override
        public Methodieken mapRow(ResultSet rs, int rowNum) throws SQLException {
            Methodieken methodiek = new Methodieken();
            methodiek.setMethodiekId(UUID.fromString(rs.getString("methodiekId")));
            methodiek.setLevelId(UUID.fromString(rs.getString("levelId")));
            methodiek.setDescription(rs.getString("description"));
            methodiek.setMethodiek(rs.getString("methodiek"));
            methodiek.setSoort(rs.getString("soort"));
            methodiek.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            methodiek.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return methodiek;
        }
    }
}