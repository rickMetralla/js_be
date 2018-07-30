package com.store.repository;

import com.store.domain.Winners;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinnerRepository extends JpaRepository<Winners, Integer> {
}
