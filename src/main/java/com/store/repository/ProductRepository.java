package com.store.repository;

import com.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
