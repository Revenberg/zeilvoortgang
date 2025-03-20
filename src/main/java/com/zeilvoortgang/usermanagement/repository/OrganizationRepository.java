package com.zeilvoortgang.usermanagement.repository;

import com.zeilvoortgang.usermanagement.entity.Organization;
import com.zeilvoortgang.usermanagement.exception.DuplicateOrganizationException;

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
public class OrganizationRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrganizationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Organization> findOrganizationById(UUID organizationId) {
        final String sql = "SELECT * FROM organizations WHERE organizationId = ?";
        List<Organization> organizations = jdbcTemplate.query(new PreparedStatementCreator() {
            @SuppressWarnings("null")
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, organizationId.toString());
                return ps;
            }
        }, new OrganizationRowMapper());
        return organizations.stream().findFirst();
    }

    public Organization saveOrganization(Organization organization) {
        final String sql = "INSERT INTO organizations (organizationId, name, lastUpdateTMS, lastUpdateIdentifier) VALUES (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql, organization.getOrganizationId().toString(), organization.getName(), organization.getLastUpdateTMS(), organization.getLastUpdateIdentifier());
        } catch (DuplicateKeyException e) {
            throw new DuplicateOrganizationException("An organization with the same name already exists.");
        } catch (Exception e) {
            throw new RuntimeException("An unexpected error occurred while saving the organization.", e);
        }
        return organization;
    }

    public Organization updateOrganization(Organization organization) {
        final String sql = "UPDATE organizations SET name = ?, lastUpdateTMS = ?, lastUpdateIdentifier = ? WHERE organizationId = ?";
        jdbcTemplate.update(sql, organization.getName(), organization.getLastUpdateTMS(), organization.getLastUpdateIdentifier(), organization.getOrganizationId().toString());
        return organization;
    }

    public void deleteByOrganizationId(UUID organizationId) {
        final String sql = "DELETE FROM organizations WHERE organizationId = ?";
        jdbcTemplate.update(sql, organizationId.toString());
    }

    private static class OrganizationRowMapper implements RowMapper<Organization> {
        @Override
        public Organization mapRow(@SuppressWarnings("null") ResultSet rs, int rowNum) throws SQLException {
            Organization organization = new Organization();
            organization.setOrganizationId(UUID.fromString(rs.getString("organizationId")));
            organization.setName(rs.getString("name"));
            organization.setLastUpdateTMS(Timestamp.valueOf(rs.getString("lastUpdateTMS")));
            organization.setLastUpdateIdentifier(rs.getString("lastUpdateIdentifier"));
            return organization;
        }
    }
}