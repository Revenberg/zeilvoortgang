package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.User;
import com.zeilvoortgang.usermanagement.exception.DuplicateUserException;

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
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findUserById(UUID userid) {
        final String sql = "SELECT * FROM users WHERE userid = ?";
        List<User> users = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, userid.toString());
                return ps;
            }
        }, new UserRowMapper());
        return users.stream().findFirst();
    }

    public User saveUser(User user) {
        final String sql = "INSERT INTO users (userid, username, email, password, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, user.getUserId().toString(), user.getUsername(), user.getEmail(), user.getPassword(), user.getLastUpdateTMS(), user.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException("A user with the same username or email already exists.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("An unexpected error occurred while saving the group.", e);
        }
        return user;
    }

    public User updateUser(User user) {
        final String sql = "UPDATE users SET username = ?, email = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE userid = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getEmail(), user.getLastUpdateTMS(), user.getLastUpdateIdentifier(), user.getUserId().toString()); 
        return user;
    }

    public void deleteByUserId(UUID userId) {
        final String sql = "DELETE FROM users WHERE userid = ?";
        jdbcTemplate.update(sql, userId.toString());
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow( @SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(UUID.fromString(rs.getString("userId")));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            user.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return user;
        }
    }
}