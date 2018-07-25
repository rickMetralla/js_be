package com.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductOrder {

    @JsonProperty
    private int prodId;

    @JsonProperty
    private int amount;

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