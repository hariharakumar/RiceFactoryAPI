package com.projects.ricefactory.mapper;

import com.projects.ricefactory.dto.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hearlapati on 12/9/16.
 */
public class UserRecordMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("u.id"));
            user.setEmail(rs.getString("u.email"));
            user.setFirstName(rs.getString("u.first_name"));
            user.setLastName(rs.getString("u.last_name"));
            user.setPassword(rs.getString("u.password"));
            user.setPhoneNumber1(rs.getString("u.phone_number_1"));
            user.setPhoneNumber2(rs.getString("u.phone_number_2"));
            user.setAddress1(rs.getString("a.address_1"));
            user.setAddress2(rs.getString("a.address_2"));
            user.setCity(rs.getString("a.city"));
            user.setState(rs.getString("a.state"));
            user.setZipCode(rs.getString("a.zip_code"));
            user.setAddressId(rs.getLong("u.address_id"));
            return user;
    }
}
