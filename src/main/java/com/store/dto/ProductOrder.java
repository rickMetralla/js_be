package com.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.domain.Order;

public class ProductOrder {

    @JsonProperty
    private int prodId;

    @JsonProperty
    private int amount;

    public ProductOrder(){
    }

    public ProductOrder(int prodId, int amount){
        this.prodId = prodId;
        this.amount = amount;
    }

    public ProductOrder (Order order){
        this.prodId = order.getProdId();
        this.amount = order.getAmount();
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}