package com.store.repository;

import com.store.domain.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Integer> {
}
