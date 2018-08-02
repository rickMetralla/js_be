package com.store.repository;

import com.store.domain.PrizeDraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface PrizeDrawRepository extends JpaRepository<PrizeDraw, Integer> {
//    @Query("SELECT custDni FROM prize_draw where promoId=:promoId")
//    public List<Integer> findDniByPromoId(@Param("promoId") Integer promoId);
}
