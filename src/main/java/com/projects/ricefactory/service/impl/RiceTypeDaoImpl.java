package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.mapper.RiceTypeMapper;
import com.projects.ricefactory.service.RiceTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by hearlapati on 2/28/17.
 */
public class RiceTypeDaoImpl implements RiceTypeDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final String SQL_RICE_TYPE_GET = "select rt.id, rt.display_name,rt.internal_name,rt.price_per_kg,rt.description from rice_type rt where internal_name=?";

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
                    SQL_RICE_TYPE_GET,
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
    public RiceType getRiceTypeById(String riceTypeId) {
        return null;
    }
}
