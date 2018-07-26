package com.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.List;

public class CustomerOrder {
    @JsonProperty
    private List<ProductOrder> productOrders;

    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date purchasedAt;

    public CustomerOrder(){
    }

    public CustomerOrder(List<ProductOrder> productOrders, Date purchasedAt){
        this.productOrders = productOrders;
        this.purchasedAt = purchasedAt;
    }

    public List<ProductOrder> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ProductOrder> productOrders) {
        this.productOrders = productOrders;
    }

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
    }
}
