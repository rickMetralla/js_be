package com.store.service;

import com.store.domain.Product;
import com.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    public Product findById(int id) {
        return repository.getOne(id);
    }

    public Product findProductByName(String name){
        return repository.findByNameContaining(name);
    }

    public List<Product> findProductByModel(String model){
        return repository.findByModel(model);
    }

    public Iterable<Product> getAll() {
        return repository.findAll();
    }

    public Product create (Product product) {
        return repository.save(product);
    }

    public Product update(Product updateProd){
        Product product = repository.getOne(updateProd.getId());
        int updateStock = product.getStock() + updateProd.getStock();
        updateProd.setStock(updateStock);
        return repository.save(updateProd);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }


}

