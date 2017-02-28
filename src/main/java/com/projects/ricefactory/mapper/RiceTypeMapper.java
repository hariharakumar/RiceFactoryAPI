package com.projects.ricefactory.mapper;

import com.projects.ricefactory.dto.RiceType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hearlapati on 2/28/17.
 */
public class RiceTypeMapper implements RowMapper<RiceType> {


    @Override
    public RiceType mapRow(ResultSet rs, int rowNum) throws SQLException {
        RiceType riceType = new RiceType();
        riceType.setId(rs.getLong("rt.id"));
        riceType.setDescription(rs.getString("rt.description"));
        riceType.setDisplayName(rs.getString("rt.display_name"));
        riceType.setInternalName(rs.getString("rt.internal_name"));
        riceType.setPrice_per_kg(rs.getFloat("rt.price_per_kg"));
        return riceType;
    }
}
