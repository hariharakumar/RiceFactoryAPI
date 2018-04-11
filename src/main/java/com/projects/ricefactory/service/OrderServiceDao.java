package com.projects.ricefactory.service;

import com.projects.ricefactory.dto.Order;
import java.util.List;

/**
 * Created by hearlapati on 2/10/17.
 */
public interface OrderServiceDao {

    public Order createOrder(Order order) throws Exception;

    public Order updateOrder(Order updatedOrder) throws Exception;

    public Order getOrderById(Long id);

    public List<Order> getAllOrders();

    public void deleteOrder(Long id);

    public List<Order> getOrdersByUserEmail(String email);
}
