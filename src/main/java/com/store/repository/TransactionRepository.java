package com.store.repository;

import com.store.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

//    @Modifying
//    @Query(value = "select cust_dni from cust_prod group by cust_dni", nativeQuery = true)
//    @Query(value = "select * from cust_prod", nativeQuery = true)
//    Iterable<Transaction> find();
}
