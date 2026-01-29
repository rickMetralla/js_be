package com.store.service;

import com.store.domain.ProductModel;
import com.store.repository.ProductModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.List;

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

    public ProductModel createNewModel(ProductModel productModel) throws HttpRequestMethodNotSupportedException{
        String validation = productModel.validateFields();
        if(validation == ""){
            ProductModel createdProductModel = repository.save(productModel);
            return createdProductModel;
        } else {
            throw new HttpRequestMethodNotSupportedException("Validation fields error. " + validation);
        }
    }

    public ProductModel updateModel(ProductModel productModel) throws HttpRequestMethodNotSupportedException{
        String validation = productModel.validateFields();
        if(validation == ""){
            ProductModel createdProductModel = repository.save(productModel);
            return createdProductModel;
        } else {
            throw new HttpRequestMethodNotSupportedException("Validation fields error. " + validation);
        }
    }

    public void deleteModel(String name){
        repository.deleteById(name);
    }

    public ProductModel getByName(String name){
        return repository.getOne(name);
    }

    public List<ProductModel> getAllByChance(int chance){
        return repository.findByChance(chance);
    }
}
