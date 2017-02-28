package com.projects.ricefactory.dto;

/**
 * Created by hearlapati on 2/10/17.
 */
public class Order {

    private String riceType;
    private float amountInKilograms;
    private float pricePerKilogram;
    private String deliveryDate;
    private String customerNotes;
    private Boolean polished;
    private Long userId;
    private Long addressId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
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

    public void setAmountInKilograms(float amountInKilograms) {
        this.amountInKilograms = amountInKilograms;
    }

    public float getPricePerKilogram() {
        return pricePerKilogram;
    }

    public void setPricePerKilogram(float pricePerKilogram) {
        this.pricePerKilogram = pricePerKilogram;
    }

    public String getDeliveryDate() {
        return deliveryDate;
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
