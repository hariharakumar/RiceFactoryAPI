package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.mapper.RiceTypeMapper;
import com.projects.ricefactory.service.RiceTypeDao;
import com.projects.ricefactory.utils.MySqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hearlapati on 2/28/17.
 */
@Service
public class RiceTypeDaoImpl implements RiceTypeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public RiceType createRiceType(RiceType riceType) throws Exception {
        return null;
    }

    @Override
    public RiceType updateRiceType(RiceType riceType) throws Exception {
        return null;
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
            /*List<RiceType> riceTypeList = this.jdbcTemplate.queryForList(
                    MySqlQueries.SQL_RICE_TYPE_ALL_GET,
                    new RiceTypeMapper());
            return riceTypeList;*/
            return null;
        }
        catch (EmptyResultDataAccessException e) {
            // Log exception
            return null;
        }
    }
}
