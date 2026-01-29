package com.store.repository;

import com.store.domain.PromoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromoStatusRepository extends JpaRepository<PromoStatus, Integer> {
    PromoStatus findPromoStatusByStatus(String status);
}
