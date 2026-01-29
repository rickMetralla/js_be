package com.store.domain;

import javax.persistence.*;

@Entity
@Table(name = "prize")
public class Prize {
    @Id
    @SequenceGenerator(name="prize_id_seq",
            sequenceName="prize_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="prize_id_seq")
    private int id;

    @Column(name = "promo_id")
    private int promoId;

    @Column(name = "name")
    private String name;

    @Column(name = "rank")
    private int rank;

    public Prize(){
    }

    public Prize(int id, int promoId, String name, int rank){
        this.id = id;
        this.promoId = promoId;
        this.name = name;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
