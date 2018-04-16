package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.Order;
import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.mapper.OrderRecordMapper;
import com.projects.ricefactory.service.OrderServiceDao;
import com.projects.ricefactory.service.RiceTypeServiceDao;
import com.projects.ricefactory.utils.MySqlQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.List;

/**
 * Created by hearlapati on 2/10/17.
 */
@Service
public class OrderServiceDaoImpl implements OrderServiceDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RiceTypeServiceDao riceTypeServiceDao;

    @Override
    public Order createOrder(Order order) throws Exception {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        //TODO: verify if rice_type_id and user_to_address_id are valid : Query DB to validate.
        RiceType riceType = riceTypeServiceDao.getRiceTypeByInternalName(order.getRiceType());
        // java8 considers this as effectively final, thats why don't have to declare it final
        Timestamp timestamp = new Timestamp(order.convertDeliveryDate().getTime());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(MySqlQueries.SQL_ORDER_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setBoolean(1, order.getPolished());
                ps.setLong(2, riceType.getId());
                ps.setFloat(3,order.getAmountInKilograms());
                ps.setFloat(4, (riceType.getPrice_per_kg()*order.getAmountInKilograms()));
                ps.setTimestamp(5, timestamp);
                ps.setLong(6, order.getUserToAddressId());
                ps.setString(7, order.getCustomerNotes());
                ps.setBoolean(8, order.getCancelled());
                return ps;
            }
        }, keyHolder);

        return getOrderById(keyHolder.getKey().longValue());
    }

    @Override
    public Order updateOrder(Order updatedOrder) throws Exception {

        long riceTypeId = riceTypeServiceDao.getRiceTypeByDisplayName(updatedOrder.getRiceType()).getId();
        Timestamp timestamp = new Timestamp(updatedOrder.convertDeliveryDate().getTime());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(MySqlQueries.SQL_ORDER_UPDATE);
                ps.setBoolean(1, updatedOrder.getPolished());
                ps.setLong(2, riceTypeId);
                ps.setFloat(3, updatedOrder.getAmountInKilograms());
                ps.setFloat(4, updatedOrder.getTotalPrice());
                ps.setTimestamp(5, timestamp);
                ps.setLong(6, updatedOrder.getUserToAddressId());
                ps.setString(7, updatedOrder.getCustomerNotes());
                ps.setBoolean(8, updatedOrder.getCancelled());
                return ps;
            }
        });

        return getOrderById(updatedOrder.getId());
    }

    @Override
    public Order getOrderById(Long id) {

        try{
            Order order = this.jdbcTemplate.queryForObject(
                    MySqlQueries.SQL_ORDER_GET,
                    new Object[]{id},
                    new OrderRecordMapper()
            );
            Long riceTypeId = Long.parseLong(order.getRiceType());
            order.setRiceType(riceTypeServiceDao.getRiceTypeById(riceTypeId).getDisplayName());
            return order;
        }
        catch (EmptyResultDataAccessException e) {

        }

        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<Order> getOrdersByUserEmail(String email) {
        List<Order> result = jdbcTemplate.query(
                            MySqlQueries.SQL_GET_ORDERS_BY_USER_EMAIL, new Object[] {email},
                            (rs, rowNum) -> new Order(
                                            rs.getLong("id"),rs.getString("display_name"), rs.getLong("amount_in_kgs"),
                                            rs.getLong("total_cost"), rs.getString("delivery_date"), rs.getString("customer_notes"),
                                            rs.getBoolean("polished"),rs.getLong("user_to_address_id"),
                                            rs.getString("date_created"), rs.getString("last_updated"), rs.getBoolean("cancelled")));

        return result;
    }
}
