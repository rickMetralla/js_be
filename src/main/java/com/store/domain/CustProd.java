package com.store.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "cust_prod")
public class CustProd implements Serializable {

    @Id
    @Column(name = "cust_dni")
    private
    int custDni;

    //@Id
    @Column(name = "prod_id")
    private
    int prodId;

    public int getCustDni() {
        return custDni;
    }

    public int getId() {
        return prodId;
    }
}
