package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.Levels;

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
public class LevelsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LevelsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Levels> findLevelById(UUID id) {
        final String sql = "SELECT * FROM levels WHERE levelId = ?";
        List<Levels> levelsList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setObject(1, id.toString());
                return ps;
            }
        }, new LevelsRowMapper());
        return levelsList.stream().findFirst();
    }

    public Levels saveLevel(Levels level) {
        final String sql = "INSERT INTO levels (levelId, description, image) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, level.getLevelId().toString(), level.getDescription(), level.getImage());
        return level;
    }

    public Levels updateLevel(Levels level) {
        final String sql = "UPDATE levels SET description = ?, image = ? WHERE levelId = ?";
        try {
            jdbcTemplate.update(sql, level.getDescription(), level.getImage(), level.getLevelId().toString());
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        return level;
    }

    public void deleteLevelById(UUID id) {
        final String sql = "DELETE FROM levels WHERE levelId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LevelsRowMapper implements RowMapper<Levels> {
        @SuppressWarnings("null")
        @Override
        public Levels mapRow(ResultSet rs, int rowNum) throws SQLException {
            Levels level = new Levels();
            level.setLevelId(UUID.fromString(rs.getString("levelId")));
            level.setDescription(rs.getString("description"));
            level.setImage(rs.getString("image"));
            return level;
        }
    }
}