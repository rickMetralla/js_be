package com.store.service;

import com.store.domain.ProductModel;
import com.store.repository.ProductModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductModelService {

    @Autowired
    ProductModelRepository repository;

    public Iterable<ProductModel> getAll(){
        return repository.findAll();
    }

    public int getChanceByModel(String modelName){
        ProductModel productModel = repository.findByName(modelName);
        return productModel.getChance();
    }
}
