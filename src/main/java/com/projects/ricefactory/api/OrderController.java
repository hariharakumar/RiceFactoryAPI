package com.projects.ricefactory.api;

import com.projects.ricefactory.dto.Order;
import com.projects.ricefactory.service.OrderServiceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by hearlapati on 2/10/17.
 */
@Controller
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired(required=true)
    private HttpServletRequest request;

    @Autowired
    private OrderServiceDao orderServiceDao;

    @RequestMapping(
            value = "/v1/orders",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createOrder(@RequestBody Order order) {

        logger.debug("Creating an Order");

        try {
            Order createdOrder = orderServiceDao.createOrder(order);

            if(createdOrder == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to create User record");
            }

            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);

        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getLocalizedMessage() + "Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/orders/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity updateOrder(@RequestBody Order updatedOrder, @PathVariable("id") Long orderId){

        logger.debug("Updating order : " + orderId);

        try {

            Order existingOrder = orderServiceDao.getOrderById(orderId);

            if(existingOrder == null) {
                return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
            }

            Order orderResponse = orderServiceDao.updateOrder(updatedOrder);

            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage() + "Error while processing request");
        }
    }

    @RequestMapping(
            value = "/v1/orders/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getOrder(@PathVariable("id") Long id) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        logger.debug("Getting order with  id : " + id);

        try {
            Order order = orderServiceDao.getOrderById(id);

            if (order == null) {
                return new ResponseEntity<Order>(responseHeaders,HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(order, responseHeaders, HttpStatus.OK);
        }catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }

    }

    @RequestMapping(
            value = "/v1/orders",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity getOrders() {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");

        logger.debug("Getting all orders for a user : ");

        try {
            Map<String, String[]> queryParamsMap = request.getParameterMap();

            String email = queryParamsMap.get("email")[0];

            logger.debug("Search parameter in the request : email : " + email);
            List<Order> orders = orderServiceDao.getOrdersByUserEmail(email);

            if (orders.size() == 0) {
                return new ResponseEntity<Order>(responseHeaders,HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity(orders, responseHeaders, HttpStatus.OK);
        }
        catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin","*").body("Error while processing request");
        }

    }
}
