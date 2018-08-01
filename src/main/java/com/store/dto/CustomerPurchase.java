package com.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomerPurchase {

    @JsonProperty
    private int custDni;

//    @JsonProperty
//    private List<ProductOrder>  productOrders;
//
//    @JsonProperty
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
//    private Date purchasedAt;

    private List<CustomerOrder> customerOrders;

    public CustomerPurchase(){
    }

    public CustomerPurchase(int custDni, List<CustomerOrder> customerOrders){
        this.custDni = custDni;
        this.setCustomerOrders(customerOrders);
    }

    public int getCustDni() {
        return custDni;
    }

    public void setCustDni(int custDni) {
        this.custDni = custDni;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString(){
        return "Customer dni: " + custDni + " Number of Orders: " + customerOrders.size();
    }
}

