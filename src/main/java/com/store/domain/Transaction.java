package com.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    @Id
    @SequenceGenerator(name="transaction_id_seq",
            sequenceName="transaction_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="transaction_id_seq")

    @Column(name = "id")
    int id;

    @Column(name = "cust_dni")
    private
    int custDni;

    @Embedded
    private Order order;

    @Column(name = "purchased_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private Date purchasedAt;

    public Transaction (){
    }

    public Transaction (int custDni, Order order, Date purchasedAt){
        this.custDni = custDni;
        this.order = order;
        this. purchasedAt = purchasedAt;
    }

    public int getId() {
        return id;
    }

    public int getCustDni() {
        return custDni;
    }

    public void setCustDni(int custDni) {
        this.custDni = custDni;
    }

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString(){
        return "Customer dni: " + this.custDni + ", Order: " +
                this.order.toString() + ", purchased at: " + this.purchasedAt.toString();
    }
}
