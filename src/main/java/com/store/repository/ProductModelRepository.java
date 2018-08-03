package com.store.repository;

import com.store.domain.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductModelRepository extends JpaRepository<ProductModel, String> {
    ProductModel findByName(String name);
}
