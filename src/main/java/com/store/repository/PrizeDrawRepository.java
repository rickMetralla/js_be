package com.store.repository;

import com.store.domain.PrizeDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PrizeDrawRepository extends JpaRepository<PrizeDraw, Integer> {
    List<PrizeDraw> findPrizeDrawByWinner(boolean win);
    List<PrizeDraw> findPrizeDrawByPromoId(int promo);
    List<PrizeDraw> findPrizeDrawByCustDni(int dni);
    List<PrizeDraw> findPrizeDrawByChances(int chance);

    PrizeDraw findPrizeDrawByCustDniAndPromoId(int dni, int promoId);
}
