package com.store.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CustomerPurchase {

    @JsonProperty
    private int custDni;

    private List<Invoice> invoices;

    public CustomerPurchase(){
    }

    public CustomerPurchase(int custDni, List<Invoice> invoices){
        this.custDni = custDni;
        this.setInvoices(invoices);
    }

    public int getCustDni() {
        return custDni;
    }

    public void setCustDni(int custDni) {
        this.custDni = custDni;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public String toString(){
        return "Customer dni: " + custDni + " Number of invoices: " + invoices.size();
    }
}

