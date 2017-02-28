package com.projects.ricefactory.service.impl;

import com.projects.ricefactory.dto.Order;
import com.projects.ricefactory.dto.RiceType;
import com.projects.ricefactory.mapper.RiceTypeMapper;
import com.projects.ricefactory.service.OrderServiceDao;
import com.projects.ricefactory.service.RiceTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
 * Created by hearlapati on 2/10/17.
 */
@Service
public class OrderServiceDaoImpl implements OrderServiceDao {

    private final String SQL_ORDER_CREATE = "insert into order(polished,rice_type_id,amount_in_kgs,total_cost,delivery_date,user_to_address_id,customer_notes) values(?,?,?,?,?,?,?)";
    private final String SQL_ORDER_UPDATE = "update order set polished=?,rice_type_id=?,amount_in_kgs=?,total_cost=?,delivery_date=?,user_to_address_id=?,customer_notes=?";
    private final String SQL_USER_TO_ADDRESS_GET = "";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RiceTypeDao riceTypeDao;

    @Override
    public Order createOrder(Order order) throws Exception {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        //TODO: verify if rice_type_id and user_to_address_id are valid : Query DB to validate.
        RiceType riceType = riceTypeDao.getRiceTypeByInternalName(order.getRiceType());

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(SQL_ORDER_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setBoolean(1, order.getPolished());
                ps.setLong(2, riceType.getId());
                ps.setFloat(3,order.getAmountInKilograms());

                return ps;
            }
        }, keyHolder);

        return null;
    }

    @Override
    public Order updateOrder(Order existingOrder, Order updatedOrder) throws Exception {
        return null;
    }

    @Override
    public Order getOrderById(Long id) {
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
    public List<Order> getOrderByUserId(String email) {
        return null;
    }
}
