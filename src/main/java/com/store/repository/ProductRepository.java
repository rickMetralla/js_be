package com.store.repository;

import com.store.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByNameContaining(String name);
    List<Product> findByModel(String model);
}
