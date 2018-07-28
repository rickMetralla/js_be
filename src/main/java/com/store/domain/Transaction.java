package com.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

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

//    @Column(name = "prod_id")
//    private
//    int prodId;

//    @Column(name = "prod_id")
////    @OneToMany(targetEntity = Product.class)
//    @OneToMany
////    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
////    @JoinColumn(table = "product", referencedColumnName = "id")
//
////    @JoinTable(name="transaction",
////            joinColumns=@JoinColumn(name="prod_id", referencedColumnName="id"),
////            inverseJoinColumns=@JoinColumn(name="tag_name", referencedColumnName="name"))
//    @JoinTable(name="transaction",
//            joinColumns=@JoinColumn(table = "product", name="prod_id", referencedColumnName="id"))
//    private List<Product> products;

//    @Transient
////    @ElementCollection
//    private
//    List<Order> orders;

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

//    public int getProdId() {
//        return prodId;
//    }
//
//    public void setProdId(int prodId) {
//        this.prodId = prodId;
//    }

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }


}
