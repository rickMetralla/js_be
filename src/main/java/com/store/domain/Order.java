package com.store.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Order implements Serializable {

    @Column(name = "prod_id")
    private int prodId;

    @Column(name = "amount")
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

    public Order (){
    }

    public Order (int id, int amount) {
        this.prodId = id;
        this.amount = amount;
    }
}
