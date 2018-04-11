package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.mapper.RiceTypeMapper;
import com.projects.ricefactory.service.RiceTypeServiceDao;
import com.projects.ricefactory.utils.MySqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by hearlapati on 2/28/17.
 */
@Service
public class RiceTypeServiceServiceDaoImpl implements RiceTypeServiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public RiceType createRiceType(RiceType riceType) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_RICE_TYPE_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, riceType.getDisplayName());
                ps.setString(2, riceType.getInternalName());
                ps.setFloat(3, riceType.getPrice_per_kg());
                ps.setString(4, riceType.getDescription());
                return ps;
            }
        }, keyHolder);

        Long riceTypeId = keyHolder.getKey().longValue();
        return getRiceTypeById(riceTypeId);
    }

    @Override
    public RiceType updateRiceType(RiceType updatedRiceType) throws Exception {

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_RICE_TYPE_UPDATE);
                ps.setString(1, updatedRiceType.getDisplayName());
                ps.setString(2, updatedRiceType.getInternalName());
                ps.setFloat(3, updatedRiceType.getPrice_per_kg());
                ps.setString(4, updatedRiceType.getDescription());
                ps.setLong(5, updatedRiceType.getId());
                return ps;
            }
        });

        return getRiceTypeById(updatedRiceType.getId());
    }

    @Override
    public RiceType getRiceTypeByInternalName(String internalName) {
        try {
            RiceType riceType = this.jdbcTemplate.queryForObject(
                    MySqlQueries.SQL_RICE_TYPE_GET_INTERNAL_NAME,
                    new Object[]{internalName},
                    new RiceTypeMapper());
            return riceType;
        }
        catch (EmptyResultDataAccessException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public RiceType getRiceTypeByDisplayName(String displayName) {
        try {
            RiceType riceType = this.jdbcTemplate.queryForObject(
                    MySqlQueries.SQL_RICE_TYPE_GET_DISPLAY_NAME,
                    new Object[]{displayName},
                    new RiceTypeMapper());
            return riceType;
        }
        catch (EmptyResultDataAccessException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public RiceType getRiceTypeById(Long riceTypeId) {
        try {
            RiceType riceType = this.jdbcTemplate.queryForObject(
                    MySqlQueries.SQL_RICE_TYPE_GET_BY_ID,
                    new Object[]{riceTypeId},
                    new RiceTypeMapper());
            return riceType;
        }
        catch (EmptyResultDataAccessException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public List<RiceType> getAllRiceTypes() {
        try {
            return this.jdbcTemplate.query(MySqlQueries.SQL_RICE_TYPE_ALL_GET, new BeanPropertyRowMapper(RiceType.class));
        }
        catch (EmptyResultDataAccessException e) {
            // Log exception
            return null;
        }
    }
}
