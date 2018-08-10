package com.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Calendar;

@Entity
//@EnableJpaAuditing
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "product")
public class Product {

    @Column(name = "name")
    private String name;

    @Id
//    @ManyToOne
    @SequenceGenerator(name="product_id_seq",
            sequenceName="product_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="product_id_seq")
    @Column(name = "id", updatable = false)
    private int id;

    @Column(name = "model")
    private String model;

    @Column(name = "stock")
    private int stock;

//    @Column(name = "date_created")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
//    private Date dateCreated;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

//    public Date getDateCreated() {
//        if (dateCreated == null)
//            dateCreated = new Date(Calendar.getInstance().getTime().getTime());
//        return dateCreated;
//    }

//    public void setDateCreated(Date dateCreated) {
//        this.dateCreated = dateCreated;
//    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
