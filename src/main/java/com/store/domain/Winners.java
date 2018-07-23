package com.store.domain;

import javax.persistence.*;

@Embeddable
@Table(name = "winners")
public class Winners {

    @Column(name = "cust_dni")
    private int custDni;

    @Column(name = "lottery_id")
    private int lotteryId;

    public int getCustDni() {
        return custDni;
    }

    public void setCustDni(int custDni) {
        this.custDni = custDni;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }
}
