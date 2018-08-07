package com.store.domain;

import javax.persistence.Column;
//import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product_model")
public class ProductModel {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "chance")
    private int chance;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
