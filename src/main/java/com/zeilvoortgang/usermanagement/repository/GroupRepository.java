package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.Group;
import com.zeilvoortgang.usermanagement.exception.DuplicateGroupException;

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
public class GroupRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Group> findGroupById(UUID groupId) {
        final String sql = "SELECT * FROM groupNames WHERE groupid = ?";
        List<Group> groupNames = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, groupId.toString());
                return ps;
            }
        }, new GroupRowMapper());
        return groupNames.stream().findFirst();
    }

    public Group saveGroup(Group group) {
        final String sql = "INSERT INTO groupNames (groupId, name, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, group.getGroupId().toString(), group.getName(), group.getLastUpdateTMS(),
                    group.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            System.out.println(e.getMessage());
            throw new DuplicateGroupException("A group with the same name already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }

        return group;
    }

    public Group updateGroup(Group group) {
        final String sql = "UPDATE groupNames SET name = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE groupId = ?";
        jdbcTemplate.update(sql, group.getName(), group.getLastUpdateTMS(), group.getLastUpdateIdentifier(),
                group.getGroupId().toString());
        return group;
    }

    public void deleteByGroupId(UUID groupId) {
        final String sql = "DELETE FROM groupNames WHERE groupId = ?";
        jdbcTemplate.update(sql, groupId.toString());
    }

    private static class GroupRowMapper implements RowMapper<Group> {
        @SuppressWarnings("null")
        @Override
        public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
            Group group = new Group();
            group.setGroupId(UUID.fromString(rs.getString("groupid")));
            group.setName(rs.getString("name"));
            group.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            group.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return group;
        }
    }
}