package com.store.domain;

import javax.persistence.*;

@Entity
@Table(name = "prize_draw")
public class PrizeDraw {

    @Id
    @SequenceGenerator(name="prize_draw_id_seq",
            sequenceName="prize_draw_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="prize_draw_id_seq")
    private
    int id;

    @Column(name = "cust_dni")
    private int custDni;

    @Column(name = "promo_id")
    private int promoId;

    @Column(name = "chances")
    private int chances;

    @Column(name = "winner")
    private boolean winner;

    @Column(name = "won_article")
    private
    Integer wonArticle;

    public PrizeDraw(){
    }

    public PrizeDraw(int custDni, int promoId, int chances, boolean winner, Integer wonArticle){
        this.custDni = custDni;
        this.promoId = promoId;
        this.chances = chances;
        this.winner = winner;
        this.wonArticle = wonArticle;
    }

    public int getCustDni() {
        return custDni;
    }

    public void setCustDni(int custDni) {
        this.custDni = custDni;
    }

    public int getPromoId() {
        return promoId;
    }

    public void setPromoId(int promoId) {
        this.promoId = promoId;
    }

    public int getId() {
        return id;
    }

    public int getChances() {
        return chances;
    }

    public void setChances(int chances) {
        this.chances = chances;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public Integer getWonArticle() {
        return wonArticle;
    }

    public void setWonArticle(Integer wonArticle) {
        this.wonArticle = wonArticle;
    }
}
