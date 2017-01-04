package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.User;
import com.projects.ricefactory.mapper.UserRecordMapper;
import com.projects.ricefactory.service.UserServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by hearlapati on 12/5/16.
 */
@Service
@Transactional(
        propagation = Propagation.SUPPORTS, // will use transactions if existing : Will not start new transactions
        readOnly = true // Saying that class level methods will be read-only : this setting can be updated for PUT,POST,DELETE methods
)
public class UserServiceDaoImpl implements UserServiceDao {

    private final String SQL_USER_GET_BY_ID = "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2,a.address_1, a.address_2,a.city,a.state,a.zip_code,u.password,u.address_id from user u, address a where u.address_id = a.id and u.id = ? ";

    private final String SQL_USER_GET_BY_EMAIL = "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2,a.address_1, a.address_2,a.city,a.state,a.zip_code,u.password,u.address_id from user u, address a where u.address_id = a.id and u.email = ? ";

    private final String SQL_ADDRESS_CREATE = "insert into address(address_1,address_2,city,state,zip_code) values(?,?,?,?,?)";
    private final String SQL_ADDRESS_UPDATE = "update address set address_1=?,address_2=?,city=?,state=?,zip_code=? where id=?";

    private final String SQL_USER_CREATE = "insert into user(first_name,last_name,email,phone_number_1, phone_number_2, password, enabled, address_id) values(?,?,?,?,?,?,?,?)";
    private final String SQL_USER_UPDATE = "update user set first_name=?,last_name=?,email=?,phone_number_1=?,phone_number_2=?,password=?,enabled=? where id=?";

    private final String SQL_GET_ALL_USERS = "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2,a.address_1, a.address_2,a.city,a.state,a.zip_code,u.password,u.address_id from user u, address a where u.address_id = a.id";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED, // Will start new transactions if they don't exist.
            readOnly = false // because this Transaction changes the database.
    )
    public User createUser(User user) throws Exception{

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // First create address record
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_ADDRESS_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getAddress1());
                ps.setString(2, user.getAddress2());
                ps.setString(3, user.getCity());
                ps.setString(4, user.getState());
                ps.setString(5, user.getZipCode());
                return ps;
            }
        }, keyHolder);

        int addressId = keyHolder.getKey().intValue();

        // Now create user record.
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_USER_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getFirstName());
                ps.setString(2, user.getLastName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPhoneNumber1());
                ps.setString(5, user.getPhoneNumber2());
                ps.setString(6, user.getPassword());
                ps.setBoolean(7, true);
                ps.setLong(8, addressId);
                return ps;
            }
        }, keyHolder);

        Long userId = keyHolder.getKey().longValue();

        return getUserById(userId);
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public User updateUser(User existingUser, User updatedUser) {

        // First update user record.
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_USER_UPDATE);
                ps.setString(1, updatedUser.getFirstName());
                ps.setString(2, updatedUser.getLastName());
                ps.setString(3, updatedUser.getEmail());
                ps.setString(4, updatedUser.getPhoneNumber1());
                ps.setString(5, updatedUser.getPhoneNumber2());
                ps.setString(6, updatedUser.getPassword());
                ps.setBoolean(7, true);
                ps.setLong(8, updatedUser.getId());
                return ps;
            }
        });

        // Now update address record
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SQL_ADDRESS_UPDATE);
                ps.setString(1, updatedUser.getAddress1());
                ps.setString(2, updatedUser.getAddress2());
                ps.setString(3, updatedUser.getCity());
                ps.setString(4, updatedUser.getState());
                ps.setString(5, updatedUser.getZipCode());
                ps.setLong(6, existingUser.getAddressId());

                return ps;
            }
        });

        return getUserById(existingUser.getId());
    }

    @Override
    public User getUserById(Long id) {

        try {
            User userObject = this.jdbcTemplate.queryForObject(
                    SQL_USER_GET_BY_ID,
                    new Object[]{id},
                    new UserRecordMapper());

            return userObject;
        } catch (EmptyResultDataAccessException e) {
            // Log the exception here.
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return this.jdbcTemplate.query(SQL_GET_ALL_USERS, new BeanPropertyRowMapper(User.class));
    }

    @Override
    @Transactional(
            propagation = Propagation.REQUIRED,
            readOnly = false
    )
    public void deleteUser(Long id) {

    }

    @Override
    public User getUserByEmail(String email) {
        User userObject = this.jdbcTemplate.queryForObject(
                SQL_USER_GET_BY_EMAIL,
                new Object[]{email},
                new UserRecordMapper());

        return userObject;
    }
}
