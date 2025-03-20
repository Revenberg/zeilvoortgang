package com.zeilvoortgang.education.repository;

import com.zeilvoortgang.education.entity.LesTrainers;
import com.zeilvoortgang.education.exception.DuplicateLesTrainersException;

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
public class LesTrainersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LesTrainersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<LesTrainers> findLesTrainersById(UUID id) {
        final String sql = "SELECT * FROM lestrainers WHERE lesTrainersId = ?";
        List<LesTrainers> lesTrainersList = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, id.toString());
                return ps;
            }
        }, new LesTrainersRowMapper());
        return lesTrainersList.stream().findFirst();
    }

    public LesTrainers saveLesTrainers(LesTrainers lesTrainers) {
        final String sql = "INSERT INTO lestrainers (lesTrainersId, lesId, trainerId, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, lesTrainers.getLesTrainersId().toString(), lesTrainers.getLesId().toString(), lesTrainers.getTrainerId().toString(), lesTrainers.getLastUpdateTMS(), lesTrainers.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateLesTrainersException("A lesTrainer with the same ID already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the lesTrainer.", e);
        }
        return lesTrainers;
    }

    public LesTrainers updateLesTrainers(LesTrainers lesTrainers) {
        final String sql = "UPDATE lestrainers SET lesId = ?, trainerId = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE lesTrainersId = ?";
        jdbcTemplate.update(sql, lesTrainers.getLesId().toString(), lesTrainers.getTrainerId().toString(), lesTrainers.getLastUpdateTMS(), lesTrainers.getLastUpdateIdentifier(), lesTrainers.getLesTrainersId().toString());
        return lesTrainers;
    }

    public void deleteByLesTrainersId(UUID id) {
        final String sql = "DELETE FROM lestrainers WHERE lesTrainersId = ?";
        jdbcTemplate.update(sql, id.toString());
    }

    private static class LesTrainersRowMapper implements RowMapper<LesTrainers> {
        @SuppressWarnings("null")
            @Override
        public LesTrainers mapRow(ResultSet rs, int rowNum) throws SQLException {
            LesTrainers lesTrainers = new LesTrainers();
            lesTrainers.setLesTrainersId(UUID.fromString(rs.getString("lesTrainersId")));
            lesTrainers.setLesId(UUID.fromString(rs.getString("lesId")));
            lesTrainers.setTrainerId(UUID.fromString(rs.getString("trainerId")));
            lesTrainers.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            lesTrainers.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return lesTrainers;
        }
    }
}