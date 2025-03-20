package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.Authorizations;
import com.zeilvoortgang.usermanagement.exception.DuplicateAuthorizationException;

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
public class AuthorizationsRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorizationsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Authorizations> findAuthorizationById(UUID authorizationId) {
        final String sql = "SELECT * FROM authorizations WHERE authorizationId = ?";
        List<Authorizations> authorizations = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, authorizationId.toString());
                return ps;
            }
        }, new AuthorizationsRowMapper());
        return authorizations.stream().findFirst();
    }

    public Authorizations saveAuthorization(Authorizations authorization) {
        final String sql = "INSERT INTO authorizations (authorizationId, description, page, raci, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, authorization.getAuthorizationId().toString(), authorization.getDescription(), authorization.getPage(), authorization.getRaci(), authorization.getLastUpdateTMS(), authorization.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateAuthorizationException("An authorization with the same ID already exists.");
        }
        return authorization;
    }

    public Authorizations updateAuthorization(Authorizations authorization) {
        final String sql = "UPDATE authorizations SET description = ?, page = ?, raci = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE authorizationId = ?";
        jdbcTemplate.update(sql, authorization.getDescription(), authorization.getPage(), authorization.getRaci(), authorization.getLastUpdateTMS(), authorization.getLastUpdateIdentifier(), authorization.getAuthorizationId().toString());
        return authorization;
    }

    public void deleteAuthorizationById(UUID authorizationId) {
        final String sql = "DELETE FROM authorizations WHERE authorizationId = ?";
        jdbcTemplate.update(sql, authorizationId.toString());
    }

    private static class AuthorizationsRowMapper implements RowMapper<Authorizations> {
        @Override
        public Authorizations mapRow(ResultSet rs, int rowNum) throws SQLException {
            Authorizations authorization = new Authorizations();
            authorization.setAuthorizationId(UUID.fromString(rs.getString("authorizationId")));
            authorization.setDescription(rs.getString("description"));
            authorization.setPage(rs.getString("page"));
            authorization.setRaci(rs.getString("raci"));
            authorization.setLastUpdateTMS(rs.getTimestamp("lastUpdateTMS"));
            authorization.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return authorization;
        }
    }
}