package com.projects.ricefactory.mapper;

import com.projects.ricefactory.dto.Order;
import com.projects.ricefactory.service.RiceTypeServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hearlapati on 2/10/17.
 */
public class OrderRecordMapper implements RowMapper<Order> {

    java.text.SimpleDateFormat sdf =
            new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("o.id"));
        order.setUserToAddressId(rs.getLong("o.user_to_address_id"));
        order.setPolished(rs.getBoolean("o.polished"));
        order.setRiceType(rs.getString("o.riceTypeId"));
        //order.setRiceType(riceTypeServiceDao.getRiceTypeById(rs.getLong("o.riceTypeId")).getDisplayName());
        order.setAmountInKilograms(rs.getLong("o.amount_in_kgs"));
        order.setTotalPrice(rs.getLong("o.total_cost"));
        order.setDeliveryDate(sdf.format(rs.getTimestamp("o.delivery_date")));
        order.setCustomerNotes(rs.getString("o.customer_notes"));
        order.setOrderCreatedDate(sdf.format(rs.getTimestamp("o.date_created")));
        order.setOrderUpdateDate(sdf.format(rs.getTimestamp("o.last_updated")));
        order.setCancelled(rs.getBoolean("o.cancelled"));
        return order;
    }
}
// select o.polished, o.riceTypeId, o.amount_in_kgs,o.total_cost,o.delivery_date,o.user_to_address_id,o.date_created,o.last_updated,o.customer_notes, o.cancelled from `order` o where o.id=2