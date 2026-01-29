package com.store.domain;

import javax.persistence.*;

@Entity
@Table(name = "rank")
public class Rank {
    @Id
    @SequenceGenerator(name="rank_id_seq",
            sequenceName="rank_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="rank_id_seq")
    private int id;

    @Column(name = "name")
    private
    String name;

    @Column(name = "description")
    private
    String description;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
