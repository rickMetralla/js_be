package com.store.repository;

import com.store.domain.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {
    List<Prize> getPrizesByPromoId(int promoId);
}
