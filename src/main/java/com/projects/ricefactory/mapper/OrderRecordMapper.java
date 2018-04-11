package com.projects.ricefactory.mapper;

import com.projects.ricefactory.dto.Order;
import com.projects.ricefactory.service.RiceTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hearlapati on 2/10/17.
 */
public class OrderRecordMapper implements RowMapper<Order> {

    @Autowired
    RiceTypeDao riceTypeDao;

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setPolished(rs.getBoolean("o.polished"));
        order.setRiceType(riceTypeDao.getRiceTypeById(rs.getLong("o.riceTypeId")).getDisplayName());
        order.setAmountInKilograms(rs.getLong("o.amount_in_kgs"));
        order.setTotalPrice(rs.getLong("o.total_cost"));
        order.setDeliveryDate(rs.getString("o.delivery_date"));
        order.setCustomerNotes(rs.getString("o.customer_notes"));
        order.setOrderCreatedDate(rs.getString("o.date_created"));
        order.setOrderUpdateDate(rs.getString("o.last_updated"));
        order.setCancelled(rs.getBoolean("o.cancelled"));
        return order;
    }
}
