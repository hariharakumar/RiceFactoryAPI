package com.projects.ricefactory.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by hearlapati on 2/10/17.
 */
public class Order {

    private Long id;
    private String riceType;
    private Long amountInKilograms;
    private Long totalPrice;
    private String deliveryDate;
    private String customerNotes;
    private Boolean polished;
    private Long userToAddressId;
    private String orderCreatedDate;
    private String orderUpdateDate;
    private Boolean cancelled;

    public Order(String riceType, Long amountInKilograms, Long totalPrice, String deliveryDate, String customerNotes, Boolean polished,
                 Long userToAddressId, String orderCreatedDate, String orderUpdateDate, Boolean cancelled) {
        this.riceType = riceType;
        this.amountInKilograms = amountInKilograms;
        this.totalPrice = totalPrice;
        this.deliveryDate = deliveryDate;
        this.customerNotes = customerNotes;
        this.polished = polished;
        this.userToAddressId = userToAddressId;
        this.orderCreatedDate = orderCreatedDate;
        this.orderUpdateDate = orderUpdateDate;
        this.cancelled = cancelled;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public String getOrderCreatedDate() {
        return orderCreatedDate;
    }

    public void setOrderCreatedDate(String orderCreatedDate) {
        this.orderCreatedDate = orderCreatedDate;
    }

    public String getOrderUpdateDate() {
        return orderUpdateDate;
    }

    public void setOrderUpdateDate(String orderUpdateDate) {
        this.orderUpdateDate = orderUpdateDate;
    }

    public Long getUserToAddressId() {
        return userToAddressId;
    }

    public void setUserToAddressId(Long userToAddressId) {
        this.userToAddressId = userToAddressId;
    }

    public String getRiceType() {
        return riceType;
    }

    public void setRiceType(String riceType) {
        this.riceType = riceType;
    }

    public float getAmountInKilograms() {
        return amountInKilograms;
    }

    public void setAmountInKilograms(Long amountInKilograms) {
        this.amountInKilograms = amountInKilograms;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDeliveryDate() throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        try {
            return dateFormat.parse(this.deliveryDate);
        }
        catch (ParseException pe) {
            throw pe;
        }
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public Boolean getPolished() {
        return polished;
    }

    public void setPolished(Boolean polished) {
        this.polished = polished;
    }
}
