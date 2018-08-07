package com.store.repository;

import com.store.domain.Promo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromoRepository extends JpaRepository<Promo, Integer> {
    List<Promo> findPromoByStatus(int status);
    List<Promo> findPromoBySeasonIsContaining(String season);
}
