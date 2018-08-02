package com.store.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "promo_status")
public class PromoStatus {

    @Id
    @SequenceGenerator(name="promo_status_id_seq",
            sequenceName="promo_status_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="promo_status_id_seq")
    @Column(name = "id")
    private int id;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    public PromoStatus(){
    }

    public PromoStatus(String status, String description){
        this.status = status;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
